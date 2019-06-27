package jh.apple.documentstore

import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

@Test
@Slf4j
@SpringBootTest
class DocumentStoreApplicationTests extends AbstractTestNGSpringContextTests {

	void sanityCheck() {
        log.debug("HERE I AM")
	}

}
