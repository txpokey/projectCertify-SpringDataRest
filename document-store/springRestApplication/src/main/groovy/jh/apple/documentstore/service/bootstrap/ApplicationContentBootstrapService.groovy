package jh.apple.documentstore.service.bootstrap

import jh.apple.documentstore.domain.AdhocDocument
import jh.apple.documentstore.repo.H2SyncAdhocDocumentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

import java.nio.charset.StandardCharsets

interface BootstrapContract{
    boolean spinUp()
}

interface ApplicationContentBootstrapContract extends BootstrapContract{}

@Service("applicationContentBootstrapService")
@Profile("default")
class ApplicationContentBootstrapService implements ApplicationContentBootstrapContract {

    final static def STANDARD_C_LANG_CHARSET = StandardCharsets.ISO_8859_1

    @Autowired
    H2SyncAdhocDocumentRepository h2SyncAdhocDocumentRepository

    @Autowired
    @Qualifier("bootstrapDataTextExample")
    String textExample

    @Override
    boolean spinUp() {
        def candidateAlaTextExample = getTextExampleDomainObject()
        assert candidateAlaTextExample
        def result = h2SyncAdhocDocumentRepository.save( candidateAlaTextExample )
        assert result
        assert result.id
        true
    }

    private getTextExampleDomainObject() {
        final def bytes = textExample.getBytes(STANDARD_C_LANG_CHARSET)
        Map m = [payload: bytes]
        def candidate = AdhocDocument.of(m)
        candidate
    }
}
