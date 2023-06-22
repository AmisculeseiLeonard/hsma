package com.healthcaresocialmedia.UserManagementService.exception;

public class UsernameAlreadyRegisteredException extends RuntimeException{
    public UsernameAlreadyRegisteredException(String message){
        super(message);
    }
}
