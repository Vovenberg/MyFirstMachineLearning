package com.killprojects.learning

import com.killprojects.Application
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Vladimir on 13.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class SVMLearningTest extends GroovyTestCase {

    @Autowired
    SVMLearning svmLearning

    @Test
    void testLearn() {
        svmLearning.learn()
    }
}
