package jh.apple.documentstore.service

import jh.apple.documentstore.domain.AdhocDocument
import jh.apple.documentstore.repo.H2SyncAdhocDocumentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

import javax.annotation.Nonnull

@Service("documentStoreService")
@Profile("default")
class DocumentStoreService implements DocumentStoreServiceContract{

    @Autowired
    H2SyncAdhocDocumentRepository repository

    @Override
    AdhocDocument save(@Nonnull AdhocDocument document) {
        assert repository
        assert document.payload
        def result = repository.saveAndFlush(document)
        assert result
        assert result.id
        result
    }

    @Override
    List<AdhocDocument> findAll() {
        def result = repository.findAll()
        assert result
        result
    }

    @Override
    Optional<AdhocDocument> findByLookupKey(@Nonnull String uuid) {
        def result = repository.findByLookupKey(uuid)
        assert result
        result
    }

}
