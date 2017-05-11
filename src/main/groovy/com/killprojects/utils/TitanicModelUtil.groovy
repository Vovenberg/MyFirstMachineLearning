package com.killprojects.utils

import com.killprojects.enums.titanic.AgeCategory
import com.killprojects.enums.titanic.PassengerClass
import com.killprojects.enums.titanic.Sex
import com.killprojects.enums.titanic.SurviveType

import static com.killprojects.enums.titanic.AgeCategory.ADULT
import static com.killprojects.enums.titanic.AgeCategory.CHILD
import static com.killprojects.enums.titanic.PassengerClass.*
import static com.killprojects.enums.titanic.Sex.FEMALE
import static com.killprojects.enums.titanic.Sex.MALE
import static com.killprojects.enums.titanic.SurviveType.DEAD
import static com.killprojects.enums.titanic.SurviveType.SURVIVED

/**
 * Created by Vladimir on 11.05.2017.
 */
class TitanicModelUtil {

    static SurviveType getSurvive(def survive) {
        switch (survive) {
            case "0":
                return DEAD
            case "1":
                return SURVIVED
        }
    }

    static Sex getSex(Object sex) {
        switch (sex) {
            case "0":
                return FEMALE
            case "1":
                return MALE

        }
    }

    static AgeCategory getAge(def age) {
        switch (age) {
            case "0":
                return CHILD
            case "1":
                return ADULT
        }
    }

    static PassengerClass getPassClass(def passClass) {
        switch (passClass) {
            case "1":
                return FIRST
            case "2":
                return SECOND
            case "3":
                return THIRD
            case "0":
                return CREW
        }
    }

}
