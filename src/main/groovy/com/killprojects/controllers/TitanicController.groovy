package com.killprojects.controllers

import com.killprojects.learning.SVM
import com.killprojects.learning.models.main.SVMParams
import com.killprojects.model.TitanicPassenger
import com.killprojects.repository.TitanicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by Vladimir on 13.05.2017.
 */
@Controller
class TitanicController {
    public static final String TITANIC_URI = "titanic"

    @Autowired
    TitanicRepository repository

    @Autowired
    SVM svm

    @RequestMapping("/titanic")
    String titanic_main(Model model) {
        def trainingInfos = repository.trainingSelection
        def testingInfos = repository.testSelection
        model.addAttribute("trainingData", trainingInfos)
        model.addAttribute("testingData", testingInfos)
        return TITANIC_URI
    }

    @RequestMapping("/titanic/train")
    String titanic_train(Model model) {
        def params = initParams()
        def training = repository.trainingSelection
        svm.train(training, params)

        model.addAttribute("trainingData", svm.getModel().toString())
        model.addAttribute("testingData", "Ожидается анализ тестовой выборки...")
        model.addAttribute("resultMessage", "Обучение прошло успешно!")
        return TITANIC_URI
    }

    static SVMParams initParams() {
        SVMParams param = new SVMParams();
        // default values
        param.svm_type = SVMParams.C_SVC;
        param.kernel_type = SVMParams.RBF;
        param.degree = 3;
        param.gamma = 0;    // 1/num_features
        param.coef0 = 0;
        param.nu = 0.5;
        param.cache_size = 100;
        param.C = 1;
        param.eps = 1e-3;
        param.p = 0.1;
        param.shrinking = 1;
        param.probability = 0;
        param.nr_weight = 0;
        param.weight_label = new int[0];
        param.weight = new double[0];
        param
    }

    @RequestMapping("/titanic/test")
    String titanic_test(Model model) {
        //def resultSVM = com.killprojects.learning.test()
        def trainingInfos = repository.trainingSelection
        model.addAttribute("trainingData", trainingInfos)
        model.addAttribute("testingData", "Результаты:\nareaUnderROC = \n areaUnderPR = ")
        model.addAttribute("resultMessage", "Анализ тестовой выборки прошел успешно!")
        return TITANIC_URI
    }

    private static GString getInfo(TitanicPassenger it) {
        "Класс:$it.passengerClass | Возраст: $it.ageCategory | Пол: $it.sex | Выживание: $it.surviveType "
    }
}
