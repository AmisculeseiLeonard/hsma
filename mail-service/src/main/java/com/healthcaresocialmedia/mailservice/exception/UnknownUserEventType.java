package com.healthcaresocialmedia.mailservice.exception;

public class UnknownUserEventType extends RuntimeException{
    public UnknownUserEventType(String message){
        super(message);
    }
}
