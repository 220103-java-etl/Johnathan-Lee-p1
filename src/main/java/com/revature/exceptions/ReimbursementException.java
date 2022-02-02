package com.revature.exceptions;

public class ReimbursementException extends RuntimeException {

    public ReimbursementException() {
        super();
    }

    public ReimbursementException(String message) {
        super(message);
    }

    public ReimbursementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReimbursementException(Throwable cause) {
        super(cause);
    }

    public ReimbursementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}