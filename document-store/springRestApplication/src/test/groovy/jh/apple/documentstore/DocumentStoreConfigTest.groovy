package jh.apple.documentstore

import groovy.util.logging.Slf4j
import jh.apple.documentstore.repo.H2SyncAdhocDocumentRepository
import jh.apple.documentstore.service.DocumentStoreServiceContract
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

@Test
@Slf4j
@SpringBootTest
class DocumentStoreConfigTest extends AbstractTestNGSpringContextTests{
    @Autowired
    @Qualifier("bootstrapDataTextExample")
    String textExample

    @Autowired
    H2SyncAdhocDocumentRepository repo

    @Autowired
    @Qualifier("documentStoreService")
    DocumentStoreServiceContract storeService

    void sanityCheck() {
        assert textExample
        assert repo
        assert storeService
        log.debug(textExample)
    }
}
