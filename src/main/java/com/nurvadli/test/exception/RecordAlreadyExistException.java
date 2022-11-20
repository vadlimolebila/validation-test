package com.nurvadli.test.exception;

public class RecordAlreadyExistException extends RuntimeException {

    public RecordAlreadyExistException() {
        super();
    }

    public RecordAlreadyExistException(String message) {
        super(message);
    }

    public RecordAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
