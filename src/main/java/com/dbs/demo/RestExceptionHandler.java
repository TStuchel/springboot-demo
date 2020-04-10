package com.dbs.demo;

import com.dbs.demo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * DEVELOPER NOTE: It's good form to always know what HTTP status code your controllers will return and under what
 * conditions. It's bad form to just return 200 for "good" and 500 for "bad". There's a rich collection of choices to
 * return specific HTTP responses depending on the result of the call.
 *
 * @see <a href="https://www.restapitutorial.com/httpstatuscodes.html">https://www.restapitutorial.com/httpstatuscodes.html</a>
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Handles any BusinessExceptions that are thrown by controllers and returns a 400 - BAD REQUEST
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest webRequest) {
        log.warn(ex.getMessage(), ex);
        return handleExceptionInternal(ex, new Error(ex.getMessage(), ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    /**
     * Catch-all that handles any non-specific RuntimeException that are thrown by controllers and returns a 500 -
     * INTERNAL SERVER ERROR
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleGenericException(RuntimeException ex, WebRequest webRequest) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, new Error(ex.getMessage() == null ? ex.toString() : ex.getMessage(), ex), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

}