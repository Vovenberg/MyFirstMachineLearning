package com.killprojects.controllers

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

    @RequestMapping("/titanic")
    String titanic_main(Model model) {
        def trainingInfos = repository.trainingSelection.collect { getInfo(it) }.join("\n")
        def testingInfos = repository.testSelection.collect { getInfo(it) }.join("\n")
        model.addAttribute("trainingData", trainingInfos)
        model.addAttribute("testingData", testingInfos)
        return TITANIC_URI
    }

    private static GString getInfo(TitanicPassenger it) {
        "Класс:$it.passengerClass | Возраст: $it.ageCategory | Пол: $it.sex | Выживание: $it.surviveType "
    }
}
