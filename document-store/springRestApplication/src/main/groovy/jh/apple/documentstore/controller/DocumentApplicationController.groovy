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
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.annotation.Nonnull
//import javax.servlet.http.HttpServletRequest

@Slf4j
@Profile(["default"])
@RestController("documentController")
@RequestMapping("/storage")
class DocumentApplicationController{

    private DocumentStoreServiceContract documentStoreService

    @Autowired
    DocumentApplicationController(
            @Qualifier("documentStoreService") DocumentStoreServiceContract documentStoreService) {
        this.documentStoreService = documentStoreService
    }

    @GetMapping("findAll")
    List<AdhocDocument> findAll() {
        def candidate = documentStoreService.findAll()
        candidate
    }
    @GetMapping("reportMedia")
    def reportOnMedia(@RequestHeader(value = HttpHeaders.CONTENT_TYPE, required = false)
                                              String contentType ) {
        String effectiveMediaType = contentType ?: MediaType.APPLICATION_JSON as String
        def candidate = reportMediaService(effectiveMediaType)
        candidate
    }

    final def static IMPL_ONE = "application/implementation_1_V1+json"
    final def static IMPL_TWO = "application/implementation_2_V1+json"
    final def static IMPL_ZERO = MediaType.APPLICATION_JSON as String

    private def reportMediaService( @Nonnull String contentType  ) {

        def candidate = null

        switch (contentType) {

            case IMPL_ZERO :

                candidate = "implementation1"
                break
            case IMPL_ONE :

                candidate = "implementation1"
                break
            case IMPL_TWO :

                candidate = "implementation2"
                break

            default:
                candidate = IMPL_ZERO
                break
        }
        ResponseEntity.status(HttpStatus.OK).body([ media: candidate])
    }

    @GetMapping("documents/{lookupKey}")
    def findByLookupKey(@Nonnull @PathVariable String lookupKey) {
        Optional<AdhocDocument> optionalCandidate = documentStoreService.findByLookupKey(lookupKey)
        def present = optionalCandidate.isPresent()
        def candidatePayload = present ? optionalCandidate.get().payload : null
        def re = present ? ResponseEntity.status(HttpStatus.OK).body(candidatePayload) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        re
    }

    @DeleteMapping("documents/{lookupKey}")
    def deleteByLookupKey(@Nonnull @PathVariable String lookupKey) {
        def success = documentStoreService.deleteByLookupKey(lookupKey)
        def re = success ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        re
    }

    @PostMapping("documents")
    def post(@RequestBody byte[] payload,
             @RequestHeader(value = HttpHeaders.CONTENT_TYPE, required = false)
                     String contentType) {
        Map map = [payload: payload]
        AdhocDocument documentPreImage = AdhocDocument.of(map)
        def storedImage = documentStoreService.save(documentPreImage)
        def lookupKey = storedImage.lookupKey
        def re = ResponseEntity.status(HttpStatus.CREATED).body(lookupKey)
        re
    }

    @PutMapping("documents/{lookupKey}")
    def put(@Nonnull @RequestBody byte[] payload,
            @Nonnull @PathVariable String lookupKey,
            @RequestHeader(value = HttpHeaders.CONTENT_TYPE, required = false)
                    String contentType) {
        def success = documentStoreService.update(lookupKey, payload)
        def re = success ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        re
    }

//    @Deprecated
//    @PostMapping("documents0")
//    def post(HttpServletRequest request) {
//        def contentType = request.getHeader(HttpHeaders.CONTENT_TYPE)
//        def inputStream = request.getInputStream()
//        byte[] requestBodyAsByteArray = IOUtils.toByteArray(inputStream)
//        Map map = [payload: requestBodyAsByteArray]
//        AdhocDocument documentPreImage = AdhocDocument.of(map)
//        def storedImage = documentStoreService.save(documentPreImage)
//        def lookupKey = storedImage.lookupKey
//        def re = ResponseEntity.status(HttpStatus.CREATED).body(lookupKey)
//        re
//    }

//    @Deprecated
//    @PutMapping("documents0/{lookupKey}")
////    @ResponseBody
//    def put(HttpServletRequest request, @Nonnull @PathVariable String lookupKey) {
//        def contentType = request.getHeader(HttpHeaders.CONTENT_TYPE)
//        def inputStream = request.getInputStream()
//        byte[] requestBodyAsByteArray = IOUtils.toByteArray(inputStream)
//        def success = documentStoreService.update(lookupKey, requestBodyAsByteArray)
//        def re = success ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
//                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
//        re
//    }
    private final def NL = "\n"

}
