package com.mcbproperty.authservice.exceptions;

public class PermissionNotFoundException extends RuntimeException {

    public PermissionNotFoundException(String message) {
        super(message);
    }

}
