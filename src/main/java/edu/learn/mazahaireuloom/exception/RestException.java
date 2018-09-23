package edu.learn.mazahaireuloom.exception;

public class RestException extends RuntimeException {
    public RestException() {
        super();
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }
}
