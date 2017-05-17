package com.killprojects.controllers

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
    public static final String TITANIC_URI = "index"

    @Autowired
    TitanicRepository repository

    @Autowired
    SVM svm

    @RequestMapping("/")
    String index(Model model){
        return titanic_main(model)
    }

    @RequestMapping("/titanic")
    String titanic_main(Model model) {
        def trainingInfos = repository.trainingSelection
        def testingInfos = repository.testSelection
        model.addAttribute("trainingData", trainingInfos)
        model.addAttribute("testingData", testingInfos)
        return TITANIC_URI
    }

    @RequestMapping("/titanic/train")
    String titanic_train(Model model,
                         @RequestParam("degree") double degree,
                         @RequestParam("gamma") double gamma
                         /*@RequestParam("coef") double coef*/) {
        def params = ParamsUtil.initParams(degree, gamma)
        def training = repository.trainingSelection
        svm.train(training, params)

        model.addAttribute("trainingData", svm.getModel().toString())
        model.addAttribute("testingData", "Ожидается анализ тестовой выборки...")
        model.addAttribute("resultMessage", "Обучение прошло успешно!")
        return TITANIC_URI
    }

    @RequestMapping("/titanic/test")
    String titanic_test(Model model) {
        def result = svm.predict(repository.testSelection)
        model.addAttribute("trainingData", svm.model.toString())
        model.addAttribute("testingData", "Результаты: $result")
        model.addAttribute("resultMessage", "Анализ тестовой выборки прошел успешно!")
        return TITANIC_URI
    }

    @RequestMapping("/titanic/clear")
    String titanic_clear(Model model) {
        svm.clearModel()
        model.addAttribute("trainingData", "")
        model.addAttribute("testingData", "")
        model.addAttribute("resultMessage", "Модель очищена!")
        return TITANIC_URI
    }

    private static GString getInfo(TitanicPassenger it) {
        "Класс:$it.passengerClass | Возраст: $it.ageCategory | Пол: $it.sex | Выживание: $it.surviveType "
    }
}
