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
    List<TitanicPassenger> convert(File file) {
        def resultList = new ArrayList()
        file.eachLine {
            def (passClass, age, sex, survive) = it.tokenize("        ")
            resultList.add(new TitanicPassenger(passengerClass: getPassClass(passClass),
                    ageCategory: getAge(age),
                    sex: getSex(sex),
                    surviveType: getSurvive(survive)
            ))
        }
        resultList
    }

    @Override
    File convert(List<TitanicPassenger> list) {
        return null
    }
}
