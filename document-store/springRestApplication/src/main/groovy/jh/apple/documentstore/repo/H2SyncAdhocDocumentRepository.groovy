package jh.apple.documentstore.repo

import jh.apple.documentstore.domain.AdhocDocument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface H2SyncAdhocDocumentRepository extends JpaRepository<AdhocDocument,Long> {}
