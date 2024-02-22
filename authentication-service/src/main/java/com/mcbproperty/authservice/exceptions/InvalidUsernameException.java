package com.mcbproperty.authservice.exceptions;

public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException(String message) {
        super(message);
    }

}
