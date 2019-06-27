package jh.apple.documentstore.service

import jh.apple.documentstore.domain.AdhocDocument
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

import javax.annotation.Nonnull

@Service("documentStoreService")
@Profile("default")
class DocumentStoreService implements DocumentStoreServiceContract{
    @Override
    List<AdhocDocument> findByUuid(@Nonnull String uuid) {
        return null
    }
}
