package com.mcbproperty.authservice.exceptions;

public class InvalidUserDataException extends RuntimeException {

    public InvalidUserDataException(String message) {
        super(message);
    }

}
