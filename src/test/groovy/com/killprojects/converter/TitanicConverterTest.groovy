package groovy.com.killprojects.converter

import com.killprojects.Application
import com.killprojects.converter.TitanicConverter
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
class TitanicConverterTest extends GroovyTestCase {

    @Autowired
    TitanicConverter converter

    @Test
    void testConvertToList() {
        def path = getClass().getResource("/titanic.txt").path
        def listPassengers = converter.convert(new FileInputStream(new File(path)))
        assert listPassengers && !listPassengers.empty
    }

    @Test
    void testConvertListToFile() {
    }
}
