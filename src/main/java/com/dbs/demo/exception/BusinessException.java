package com.dbs.demo.exception;

/**
 * This exception type is used to distinguish business-layer exceptions from other types of exceptions.
 */
public class BusinessException extends Exception {

    // ------------------------------------------------- CONSTRUCTORS --------------------------------------------------

    public BusinessException(String message) {
        super(message);
    }

}
