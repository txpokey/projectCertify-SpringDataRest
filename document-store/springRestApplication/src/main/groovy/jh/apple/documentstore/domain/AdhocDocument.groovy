package jh.apple.documentstore.domain

import javax.annotation.Nonnull
import javax.persistence.*

@Entity
class AdhocDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    String lookupKey // use a guid here
    String loggingKey // use a guid here

    @Lob
    @Column(name = "payload", columnDefinition="BLOB")
    private byte[] payload

    String toString() { "[${lookupKey}, ${loggingKey} ]"}

    static String getUuid() {
        UUID.randomUUID() as String
    }
    static AdhocDocument of( @Nonnull map ) {
        assert map.payload
        def uuid    = map.uuid    ?: getUuid()
        def uuidLog = map.uuidLog ?: uuid
        Map payload = [ loggingKey: uuid , lookupKey: uuid , payload: map.payload ]
        def candidate = new AdhocDocument( payload )
        candidate
    }
}
