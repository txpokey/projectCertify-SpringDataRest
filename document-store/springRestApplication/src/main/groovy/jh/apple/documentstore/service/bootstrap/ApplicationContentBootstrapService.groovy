package jh.apple.documentstore.service.bootstrap

import groovy.util.logging.Slf4j
import jh.apple.documentstore.domain.AdhocDocument
import jh.apple.documentstore.service.DocumentStoreServiceContract
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

import javax.annotation.Nonnull
import java.nio.charset.StandardCharsets

interface BootstrapContract{
    boolean spinUp()
}

interface ApplicationContentBootstrapContract extends BootstrapContract{}
@Slf4j
@Service("applicationContentBootstrapService")
@Profile("default")
class ApplicationContentBootstrapService implements ApplicationContentBootstrapContract {

    final static def STANDARD_C_LANG_CHARSET = StandardCharsets.ISO_8859_1

    @Autowired
    @Qualifier("documentStoreService")
    DocumentStoreServiceContract documentStoreService

    @Autowired
    @Qualifier("bootstrapDataTextExample")
    String textExample

    @Override
    boolean spinUp() {
        def candidateAlaTextExample = getTextExampleDomainObject()
        assert candidateAlaTextExample
        assert candidateAlaTextExample.lookupKey
        def preImageLookupKey = candidateAlaTextExample.lookupKey ?: "BAD_PREIMAGE_LOOKUP_KEY"

        def result = documentStoreService.save( candidateAlaTextExample )
        assert result
        assert result.id
        assert result.lookupKey
        def resultLookupKey = result.lookupKey ?: "BAD_RESULT_LOOKUP_KEY"
        assert preImageLookupKey == resultLookupKey
        assert candidateAlaTextExample == result
        def findByResults = getFindByResults( resultLookupKey )
        log.debug("spinUp:> ${resultLookupKey}")
        true
    }

    private AdhocDocument getFindByResults( @Nonnull String uuid ) {
        Optional<AdhocDocument> optional = documentStoreService.findByLookupKey(uuid)
        assert optional
        assert optional.isPresent()
        def candidate = optional.get()
        assert candidate.id
        assert candidate.lookupKey == uuid
        candidate
    }
    private AdhocDocument getTextExampleDomainObject() {
        final def bytes = textExample.getBytes(STANDARD_C_LANG_CHARSET)
        Map m = [payload: bytes]
        def candidate = AdhocDocument.of(m)
        candidate
    }
}
