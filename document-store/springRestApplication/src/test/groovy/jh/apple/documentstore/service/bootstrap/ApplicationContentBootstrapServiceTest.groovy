package jh.apple.documentstore.service.bootstrap

import groovy.util.logging.Slf4j
import jh.apple.documentstore.repo.H2SyncAdhocDocumentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

@Test
@Slf4j
@SpringBootTest
class ApplicationContentBootstrapServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    @Qualifier("dummy")
    String myDummy

    @Autowired
    H2SyncAdhocDocumentRepository repo

    void sanityCheck() {
        assert myDummy
        assert repo
    }

    void testSpinUp() {
        def service = new ApplicationContentBootstrapService()
        assert service
        def result = service.spinUp()
        assert result
    }
}