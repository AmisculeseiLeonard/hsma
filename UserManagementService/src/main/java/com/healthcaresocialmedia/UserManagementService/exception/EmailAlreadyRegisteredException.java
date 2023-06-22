package com.healthcaresocialmedia.UserManagementService.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String message){
        super(message);
    }
}
