package jh.apple.documentstore.service

import jh.apple.documentstore.domain.AdhocDocument

import javax.annotation.Nonnull

interface DocumentStoreServiceContract{

    AdhocDocument save(@Nonnull AdhocDocument document)

    List<AdhocDocument> findAll()

    Optional<AdhocDocument> findByLookupKey(@Nonnull String uuid)

    boolean deleteByLookupKey(@Nonnull String uuid)

    boolean update(@Nonnull String lookupKey, @Nonnull byte[] newPayload )

}
