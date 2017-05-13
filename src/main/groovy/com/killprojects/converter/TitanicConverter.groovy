package com.killprojects.converter

import com.killprojects.model.TitanicPassenger
import org.springframework.stereotype.Component

import static com.killprojects.utils.TitanicModelUtil.*
/**
 * Created by Vladimir on 11.05.2017.
 */
@Component
class TitanicConverter implements Converter<TitanicPassenger> {

    @Override
    List<TitanicPassenger> convert(InputStream data) {
        def resultList = new ArrayList()
        data.readLines().each {
            def (passClass, age, sex, survive) = it.tokenize("        ")
            resultList.add(new TitanicPassenger(passengerClass: getPassClass(passClass),
                    ageCategory: getAge(age),
                    sex: getSex(sex),
                    surviveType: getSurvive(survive)
            ))
        }
        shuffleList(resultList)
    }

    @Override
    InputStream convert(List<TitanicPassenger> list) {
        return null
    }

    private static List shuffleList(ArrayList resultList) {
        Collections.shuffle(resultList)
        resultList
    }
}
