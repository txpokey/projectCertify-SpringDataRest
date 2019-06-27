package jh.apple.documentstore.service.bootstrap

import groovy.util.logging.Slf4j
import jh.apple.documentstore.DocumentStoreConfigTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.testng.annotations.Test

@Test
@Slf4j
@SpringBootTest
class ApplicationContentBootstrapServiceTest extends DocumentStoreConfigTest {

    @Autowired
    @Qualifier("applicationContentBootstrapService")
    ApplicationContentBootstrapContract service

    void sanityCheck() {
        super.sanityCheck()
        assert super,repo
        assert service
    }

    void testSpinUp() {
        assert service
        def result = service.spinUp()
        assert result
    }
}