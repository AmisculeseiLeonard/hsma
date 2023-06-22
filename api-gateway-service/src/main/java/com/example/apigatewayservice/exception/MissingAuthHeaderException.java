package com.example.apigatewayservice.exception;

public class MissingAuthHeaderException extends RuntimeException{
    public MissingAuthHeaderException(String message){
        super(message);
    }
}
