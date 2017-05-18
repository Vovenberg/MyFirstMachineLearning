package com.killprojects.controllers

import com.killprojects.enums.titanic.SurviveType
import com.killprojects.learning.SVM
import com.killprojects.model.TitanicPassenger
import com.killprojects.repository.TitanicRepository
import com.killprojects.utils.ParamsUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * Created by Vladimir on 13.05.2017.
 */
@Controller
class TitanicController {
    public static final String TITANIC_URI = "training"
    public static final String GUESS_URI = "guess"

    @Autowired
    TitanicRepository repository

    @Autowired
    SVM svm

    def message

    @RequestMapping("/titanic")
    String titanic_main(Model model) {
        def trainingInfos = repository.trainingSelection
        def testingInfos = repository.testSelection
        model.addAttribute("trainingData", trainingInfos)
        model.addAttribute("testingData", testingInfos)
        model.addAttribute("gamma", 0.05)
        model.addAttribute("nu", 0.5)
        return TITANIC_URI
    }

    @RequestMapping("/titanic/train")
    String titanic_train(Model model,
                         @RequestParam("gamma") double gamma,
                         @RequestParam("nu") double nu) {
        def params = ParamsUtil.initParamsRBF(gamma, nu)
        def training = repository.trainingSelection
        svm.train(training, params)

        model.addAttribute("trainingData", svm.getModel().toString())
        model.addAttribute("testingData", "Ожидается анализ тестовой выборки...")
        model.addAttribute("resultMessage", "Обучение прошло успешно!")
        model.addAttribute("gamma", (int) gamma)
        model.addAttribute("nu", nu)
        return TITANIC_URI
    }

    @RequestMapping("/titanic/test")
    String titanic_test(Model model) {
        def testSelection = repository.testSelection
        def result = svm.predict(testSelection)

        model.addAttribute("trainingData", svm.model.toString())
        model.addAttribute("testingData", "Результат.\n Процент успешных классификаций: $result")
        model.addAttribute("resultMessage", "Анализ тестовой выборки прошел успешно!")
        model.addAttribute("gamma", (int) Math.floor(svm.model.param.gamma))
        model.addAttribute("nu", svm.model.param.nu)
        return TITANIC_URI
    }

    @RequestMapping("/titanic/clear")
    String titanic_clear(Model model) {
        svm.clearModel()
        model.addAttribute("trainingData", "")
        model.addAttribute("testingData", "")
        model.addAttribute("resultMessage", "Модель очищена!")
        model.addAttribute("gamma", 0.05)
        model.addAttribute("nu", 0.5)
        return TITANIC_URI
    }

    @RequestMapping("/guess")
    String titanic_guess(Model model) {
        def params = ParamsUtil.initParamsRBF(0.05, 0.5)
        def training = repository.trainingSelection
        svm.train(training, params)

        def testSelection = repository.testSelection
        def result = svm.predict(testSelection)
        message = "Процент успешного прогнозирования: $result"
        model.addAttribute("train", message)
        return GUESS_URI
    }

    @RequestMapping("/titanic/guess")
    String titanic_guess(Model model,
                         @RequestParam("passClass") int passClass,
                         @RequestParam("sex") int sex,
                         @RequestParam("age") int age) {
        def result = svm.predictOneVector([passClass, sex, age])
        model.addAttribute("result", result == -1.0 ? "К сожалению Вы не выживете(" : "Вы спаслись!")
        model.addAttribute("passClass", passClass)
        model.addAttribute("sex", sex)
        model.addAttribute("age", age)
        model.addAttribute("train", message)
        return GUESS_URI
    }

    private static GString getInfo(TitanicPassenger it) {
        "Класс:$it.passengerClass | Возраст: $it.ageCategory | Пол: $it.sex | Выживание: $it.surviveType "
    }
}
