package jh.apple.documentstore.domain

import javax.annotation.Nonnull
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob

@Entity
class AdhocDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    String uuid
    String uuidLog

    @Lob
    @Column(name = "payload", columnDefinition="BLOB")
    private byte[] payload

    String toString() { "[${uuid}, ${uuidLog} ]"}

    static String getUuid() {
        UUID.randomUUID() as String
    }
    static AdhocDocument of( @Nonnull map ) {
        assert map.payload
        def uuid    = map.uuid    ?: getUuid()
        def uuidLog = map.uuidLog ?: uuid
        Map payload = [ uuid: uuid , uuidLog: uuidLog , payload: map.payload ]
        def candidate = new AdhocDocument( payload )
        candidate
    }
}
