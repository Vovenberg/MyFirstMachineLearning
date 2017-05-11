package com.killprojects.model

import com.killprojects.enums.titanic.AgeCategory
import com.killprojects.enums.titanic.PassengerClass
import com.killprojects.enums.titanic.Sex
import com.killprojects.enums.titanic.SurviveType

/**
 * Created by Vladimir on 11.05.2017.
 */
//Attributes:
//1 CLASS     u  1st 2nd 3rd crew  # Passenger class, or crewmember
//2 AGE       u  child adult       # Age category
//3 SEX       u  female male       # Sex
//4 SURVIVED  u  no yes            # Did they survive?
class TitanicPassenger {
    PassengerClass passengerClass
    AgeCategory ageCategory
    Sex sex
    SurviveType surviveType

}
