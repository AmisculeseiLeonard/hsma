package com.healthcaresocialmedia.UserManagementService.exception;

public class UserAuthenticationFailedException extends RuntimeException{
    public UserAuthenticationFailedException(String message){
        super(message);
    }
}
