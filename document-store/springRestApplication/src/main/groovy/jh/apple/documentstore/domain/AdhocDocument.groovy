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

    static String computeLookupKey() {
        String ret = UUID.randomUUID().toString()
        ret
    }
    static AdhocDocument of( @Nonnull map ) {
        assert map.payload
        final String computedKey = computeLookupKey()
        final String lookupKey  = map.lookupKey    ?: computedKey
        final String loggingKey = map.loggingKey   ?: lookupKey
        final Map payload = [ loggingKey: loggingKey , lookupKey: lookupKey , payload: map.payload ]
        final AdhocDocument candidate = new AdhocDocument( payload )
        candidate
    }
    static Map<String,Object> toMap( @Nonnull document ) {
        def id = document.id
        def loggingKey = document.loggingKey
        def lookupKey = document.lookupKey
        def payload = document.payload
        def candidate = [ id: id , loggingKey: loggingKey , lookupKey: lookupKey , payload: payload ]
        candidate
    }
}
