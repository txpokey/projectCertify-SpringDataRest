package explore

import groovy.util.logging.Slf4j
import jh.apple.documentstore.domain.AdhocDocument
import org.testng.annotations.Test

import javax.annotation.Nonnull

@Test
@Slf4j
class ExploreMapHoldingBytesTest{

    private final byte[] byteExample = [ 0xde , 0xad, 0xbe, 0xef ]

    void sanityCheck() {

        Map payload = [ uuid: getUuid() , uuidLog: getUuid() , payload: byteExample ]
        def candidate = new AdhocDocument( payload )
        def of = AdhocDocument.of(payload)
        log.debug("HERE")
    }

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
