package com.mcbproperty.authservice.exceptions;

public class InvalidUserIdentifierException extends RuntimeException {

    public InvalidUserIdentifierException(String message) {
        super(message);
    }

}
