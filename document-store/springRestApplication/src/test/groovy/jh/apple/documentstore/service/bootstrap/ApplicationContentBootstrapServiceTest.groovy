package jh.apple.documentstore.service.bootstrap

import groovy.util.logging.Slf4j
import org.testng.annotations.Test

@Test
@Slf4j
public class ApplicationContentBootstrapServiceTest {

    void testSpinUp() {
        def service = new ApplicationContentBootstrapService()
        assert service
        def result = service.spinUp()
        assert result
    }
}