package jh.apple.documentstore.service

import jh.apple.documentstore.domain.AdhocDocument

import javax.annotation.Nonnull

interface DocumentStoreServiceContract{

//    AdhocDocument save(@Nonnull AdhocDocument document )

    List<AdhocDocument> findByUuid( @Nonnull String uuid )

}
