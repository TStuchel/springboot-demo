package com.dbs.demo

import com.dbs.demo.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.WebRequest

class RestExceptionHandlerSpec extends BaseSpecification {

    // ------------------------------------------------ DOCUMENTATION --------------------------------------------------

    def setupSpec() {
        reportHeader """
        <br/>
        Exceptions thrown by the application are handled in a centralized module in order to respond with a consistent 
        error message structure.
        <br/>
        """
    }


    // ----------------------------------------------- MEMBER VARIABLES ------------------------------------------------

    RestExceptionHandler restExceptionHandler = new RestExceptionHandler()


    // ------------------------------------------------ SPECIFICATIONS -------------------------------------------------

    def "Handle Business Exception"() {

        // --
        given: "a BusinessException is thrown"
        BusinessException expectedException = podamFactory.manufacturePojo(BusinessException)
        WebRequest webRequestMock = Mock(WebRequest)

        // --
        when: "the BusinessException is handled"
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleBusinessException(expectedException, webRequestMock)

        // --
        then: "a response should be returned"
        Throwable actualException = (Throwable) responseEntity.getBody()
        actualException != null

        and: "it should indicate an HTTP status code of 400 - Bad Request"
        responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST

        and: "it should contain the error message text"
        actualException.getMessage() == expectedException.getMessage()
    }

    def "Handle Generic Exception"() {

        // --
        given: "a generic exception is thrown"
        RuntimeException expectedException = podamFactory.manufacturePojo(NullPointerException.class)
        WebRequest webRequestMock = Mock(WebRequest)

        // --
        when: "the exception is handled"
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleGenericException(expectedException, webRequestMock)

        // --
        then: "a response should be returned"
        Throwable actualException = (Throwable) responseEntity.getBody()
        actualException != null

        and: "it should indicate an HTTP status code of 500 - Internal Server Error"
        responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR

        and: "it should contain the error message text"
        actualException.getMessage().contains("NullPointerException")
    }

}
