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
    List<AdhocDocument> findByUuid(@Nonnull String uuid) {
        return null
    }
}
