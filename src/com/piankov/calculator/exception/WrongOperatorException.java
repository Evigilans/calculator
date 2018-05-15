package com.piankov.calculator.exception;

public class WrongOperatorException extends Exception {
    public WrongOperatorException() {
        super();
    }

    public WrongOperatorException(String message) {
        super(message);
    }

    public WrongOperatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongOperatorException(Throwable cause) {
        super(cause);
    }
}
