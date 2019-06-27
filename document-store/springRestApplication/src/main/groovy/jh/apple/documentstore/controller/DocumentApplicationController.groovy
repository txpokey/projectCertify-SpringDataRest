package jh.apple.documentstore.controller

import groovy.util.logging.Slf4j
import jh.apple.documentstore.service.DocumentStoreServiceContract
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@Profile(["deadbeef"])
@RestController("documentController")
@RequestMapping("/storage")
class DocumentApplicationController{

    private DocumentStoreServiceContract documentStoreService

    @Autowired
    DocumentApplicationController(@Qualifier("gateWayServerMapForRoundRobin") DocumentStoreServiceContract documentStoreService) {
        this.documentStoreService = documentStoreService
    }

//    @PostMapping
}
