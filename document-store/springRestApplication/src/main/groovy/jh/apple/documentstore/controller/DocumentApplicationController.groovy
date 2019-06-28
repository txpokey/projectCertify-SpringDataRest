package jh.apple.documentstore.controller

import groovy.util.logging.Slf4j
import jh.apple.documentstore.domain.AdhocDocument
import jh.apple.documentstore.service.DocumentStoreServiceContract
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

@Slf4j
@Profile(["default"])
@RestController("documentController")
@RequestMapping("/storage")
class DocumentApplicationController{

    private DocumentStoreServiceContract documentStoreService

    @Autowired
    DocumentApplicationController(@Qualifier("documentStoreService") DocumentStoreServiceContract documentStoreService) {
        this.documentStoreService = documentStoreService
    }

    @GetMapping("findAll")
    List<AdhocDocument> findAll() {
        def candidate = documentStoreService.findAll()
        candidate
    }

    @PostMapping("documents")
    @ResponseBody
    def post(HttpServletRequest request) throws IOException {
        def contentType = request.getHeader(HttpHeaders.CONTENT_TYPE)
        def inputStream = request.getInputStream()
        byte[] requestBodyAsByteArray = IOUtils.toByteArray(inputStream)
        Map map = [ payload: requestBodyAsByteArray ]
        AdhocDocument documentPreImage = AdhocDocument.of(map)
        def storedImage = documentStoreService.save(documentPreImage)
//        def size = requestBodyAsByteArray.size()
        final def NL = "\n"
        def lookupKey = storedImage.lookupKey
        def re = ResponseEntity.status(HttpStatus.CREATED).body(lookupKey)
        re
    }
}
