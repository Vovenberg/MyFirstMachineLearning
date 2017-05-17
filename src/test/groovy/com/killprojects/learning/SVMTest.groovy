package com.killprojects.learning

import com.killprojects.Application
import com.killprojects.learning.models.main.SVMParams
import com.killprojects.repository.TitanicRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Vladimir on 16.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class SVMTest extends GroovyTestCase {
    @Autowired
    SVM svm

    @Autowired
    TitanicRepository repository

    @Test
    void testTrain() {
        svm.train(repository.trainingSelection,new SVMParams())
        assert true
    }
}
