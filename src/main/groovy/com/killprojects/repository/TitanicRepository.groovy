package com.killprojects.repository

import com.killprojects.converter.Converter
import com.killprojects.converter.TitanicConverter
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
    public static final String FILENAME = "titanicData.txt"

    @Autowired
    TitanicConverter converter

    @Autowired
    Loader loader

    private List<List<Integer>> trainData
    private List<List<Integer>> testData

    @PostConstruct
    def init() {
        def inputStream = loader.loadInputStreamWithData(FILENAME, true)
        def data = converter.convertToQMatrix(inputStream).collate(2000)
        trainData = data[0]
        testData = data[1]
    }

    List<List<Integer>> getTrainingSelection() {
        return trainData
    }

    List<List<Integer>> getTestSelection() {
        return testData
    }
}
