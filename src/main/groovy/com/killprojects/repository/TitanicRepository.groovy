package com.killprojects.repository

import com.killprojects.converter.Converter
import com.killprojects.model.TitanicPassenger
import com.killprojects.utils.Loader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

/**
 * Created by Vladimir on 11.05.2017.
 */
@Component
class TitanicRepository {
    public static final String FILENAME = "titanic.txt"

    @Autowired
    Converter converter

    @Autowired
    Loader loader

    private List<TitanicPassenger> data

    @PostConstruct
    def init() {
        def file = loader.loadInputStreamWithData(FILENAME, true)
        data = converter.convert(file)
    }

    List<TitanicPassenger> getAllData() {
        return data
    }

    List<TitanicPassenger> getTrainingSelection() {
        return data.collate(2000)[0]
    }

    List<TitanicPassenger> getTestSelection() {
        return data.collate(2000)[1]
    }
}
