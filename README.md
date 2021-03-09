# Programming Challenge - 

## Task Description
* Implement a Java Web Application that meets the specification of the "Document Storage REST Web Service" below.
* Limit the scope to the specification. The only error cases to be aware of are those outlined in the specification.
* The web application should be packaged as a WAR and should run in Tomcat and Java 1.8.
* Documents don't need to be persisted across server shutdown.
* Documents metadata (like file name and size) should be stored in a in memory DB 

Implement the below service using Spring Boot & Java 8.

## Document Storage REST Web Service Specification
* The Document Storage Service is a simple RESTful web service that allows clients to create, update, query, and 
* delete documents. A document can be anything - text, image, pdf, etc.
* A document can be created by sending a POST request with document contents to /storage/documents. The document is simply the HTTP request payload. All content types are supported. The content of the POST response is a unique alphanumeric document ID with a length of 20 characters. The HTTP response has a 201 Created status code.
* A document can be queried by sending a GET request to /storage/documents/{docId}, where {docId} is the document ID issued during creation. The content of the GET response is the document exactly as it was created or last updated. On success, a 200 OK response is sent. A 404 Not Found HTTP response is returned if the document ID is invalid.
* A document can be updated by sending a PUT request with document contents to /storage/documents/{docId}, where {docId} is the document ID issued during creation. The document is simply the HTTP request payload. On success, a 204 No Content response is sent. A 404 Not Found HTTP response is returned if the document ID is invalid.
* A document can be deleted by sending a DELETE request with no content to /storage/documents/{docId}, where {docId} is the document ID issued during creation. On success, a 204 No Content HTTP response is sent. A 404 Not Found HTTP response is returned if the document ID is invalid.
```
    Summary
    Create - POST /storage/documents
    Query - GET /storage/documents/{docId} 
    Update - PUT /storage/documents/{docId} 
    Delete - DELETE /storage/documents/{docId}
     
    Examples:
    
    Create
    
    Request:
    
    POST /storage/documents
    Content-Length: 11
    hello world
    
    Response:
    201 Created
    Content-Type: text/plain; charset=us-ascii Content-Length: 20
    ONWZ4UUVV8S31JCB662P
    
    Query
    
    Request:
    GET /storage/documents/ONWZ4UUVV8S31JCB662P
    
    Response:
    200 OK
    Content-Length: 11
    hello world
    
    Update
    
    Request:
    PUT /storage/documents/ONWZ4UUVV8S31JCB662P Content-Length: 13
    goodbye world
    
    Response:
    204 No Content
    
    Delete
    Request:
    DELETE /storage/documents/ONWZ4UUVV8S31JCB662P
    
    Response:
    204 No Content
```
-------------------------------------------------------------------------------------------


# Commands for testing API

* curl -i -X GET localhost:8080/storage/documents/91924ed8-9a6b-4846-87bc-1d5bd71b7a84
* curl -i -X POST localhost:8080/storage/documents   -H "Content-Type: application/pdf"   --data-binary @myCountry.pdf
* curl -i -X PUT localhost:8080/storage/documents/91924ed8-9a6b-4846-87bc-1d5bd71b7a84 -H "Content-Type: text/plain; charset=us-ascii"   --data-binary @daveBowman
* curl -i -X DELETE localhost:8080/storage/documents/7b3d9794-cefd-4125-8a43-a76d49104eea

## Example Test Data and Test Data results

* look in springRestApplication/etc/notes/testing/documents for the raw test data
* look in springRestApplication/etc/notes/testing/results for the transcript of the commands and their output

# Rest API URIs

This solution also presents an api exposing the repo ala Spring Data Rest. The base URI is set in the 
applications-default.yml as "/rest". 

The HAL/APLS api is autoconfigured, so relevant uri for the API discovery are as follows: 

* http://localhost:8080/rest/profile
* http://localhost:8080/rest/adhocDocuments
* http://localhost:8080/rest/browser/index.html#/rest/
* http://localhost:8080/rest/profile/adhocDocuments

# Additional Features

* there's a ["findAll" endpoint](http://localhost:8080/storage/findAll) in addition to discovering the database contents via the HAL/ALPS methodology

# Design Issues

* Use of groovy: 
   * groovy of course is the next generation Java, and it has it's own [Groovy Language JSR 241](https://jcp.org/en/jsr/detail?id=241)
   * for the most part, this solution is only using 4 groovy idioms: List, Map, def, constructors by maps, and the 
elimination of getters or setters but without the ceremony of [Project Lombok annotations](https://projectlombok.org/) 
   * I could have used Intellij to automagically convert the groovy to straight Java, but in reality this is a rapid 
prototyping example and groovy is ideal for those use case scenarios.  
As to production, I've deployed groovy grails applications on at least 4 projects since 2010, and in fact have been working production Spring micro-services 
exclusively in the past year. 
* following Spring/Grails conventions: 
   * domain objects
   * services implementing contracts
   * controllers using SpingBoot-SpringRest Mappings: e.g. @PostMapping 
      * controllers should deletate all processing to service components, which return what the controllers need to decide to: 
         * forward
         * redirect
         * render

# Deprecated Methods: 

* with the exception of two @Deprecated endpoints-methods, the DocumentApplicationController can be tested as an 
ordinary Spring injected (DI) class. 
* The  @Deprecated endpoints-methods are in this code, to compare and to contrast the anti-pattern of exposing HttpServletRequest
   * in the application controller, there are two @Deprecated methods for PUT and POST respectively, each 
exposing the HttpServletRequest object. This is a Spring anti-pattern, since Spring MVC 3. 
   * In those implementations, the request is used to grab and to drain the InputStream into a byte[] payload for the 
AdhocDocment domain object. 
   * But exposing (e.g.) network objects like HttpServletRequest make testing the controller much harder, because it's a
 pain to mock a network object. 
* The much preferred approach is to directly use the @RequestBody byte[] payload syntax, which eliminates the 
appearance of the HttpServletRequest in the PUT and POST implementations. 

-------------------------------------------------------------------------------------------
# Distribution

* the instructions were to use spring boot and produce a war. I provided both a war and an executable jar. 
* spring boot is typically deployed (esp. as cloud micro-services) as an executable jar. 
   * I added the "war" plugin to the top level build.gradle and used the bootwar task to create the war. 
   * I also used the bootjar task to create the springBoot executable jar. 
* I placed the bootjar and bootwar artifacts in the following directory: springRestApplication/etc/dist
   * I did not test the war, as I dont have time to install a separate tomcat. 
   * instead I only tested the application via bootjar or via IntelliJ 

