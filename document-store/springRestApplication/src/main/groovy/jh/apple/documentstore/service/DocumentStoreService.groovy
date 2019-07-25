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

    final private saveInRepositoryMethodReference = this.&saveInRepositoryCrud

    @Override
    AdhocDocument save(@Nonnull AdhocDocument document) {
        assert repository
        assert document.payload
        def result = saveInRepositoryMethodReference(document)
        assert result && result.id
        result
    }

    /**
     * CrudRepository version
     * @param document
     * @return
     */
    private saveInRepositoryCrud(AdhocDocument document) {
        repository.save(document)
    }
    /**
     * saveAndFlush is part of JPA repository by not CrudRepository
     * @param document
     * @return
     */
    private saveInRepositoryJpa(AdhocDocument document) {
        repository.saveAndFlush(document)
    }

    @Override
    List<AdhocDocument> findAll() {
        def result = repository.findAll()
        result
    }

    @Override
    Optional<AdhocDocument> findByLookupKey(@Nonnull String uuid) {
        def result = repository.findByLookupKey(uuid)
        result
    }

    @Override
    boolean deleteByLookupKey(@Nonnull String uuid) {
        Optional<AdhocDocument> discoveredDocumentOptional = findByLookupKey(uuid)
        def wasDiscovered = discoveredDocumentOptional.present
        if ( wasDiscovered ) {
            def documentDiscovered = discoveredDocumentOptional.get()
            repository.delete(documentDiscovered)
        }
        wasDiscovered
    }

    @Override
    boolean update(@Nonnull String lookupKey, @Nonnull byte[] newPayload ) {
        Optional<AdhocDocument> discoveredDocumentOptional = findByLookupKey(lookupKey)
        def wasDiscovered = discoveredDocumentOptional.present
        def wasUpdated = false
        if ( wasDiscovered ) {
            AdhocDocument documentDiscovered = discoveredDocumentOptional.get()
            def newLoggingKey = AdhocDocument.computeLookupKey()
            documentDiscovered.payload = newPayload
            documentDiscovered.loggingKey = newLoggingKey
            def revisedDocument = save(documentDiscovered)
            wasUpdated = true
        }
        def success = wasDiscovered && wasUpdated
        success
    }
}
