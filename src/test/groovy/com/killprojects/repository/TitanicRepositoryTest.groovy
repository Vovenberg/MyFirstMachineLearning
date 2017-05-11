package com.killprojects.repository

import com.killprojects.Application
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Vladimir on 11.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class TitanicRepositoryTest extends GroovyTestCase {
    @Autowired
    TitanicRepository repository

    @Test
    void testGetAllData() {
        def data = repository.getAllData()
        assert data
        assert !data.empty
    }
}
