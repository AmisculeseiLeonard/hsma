package com.example.apigatewayservice.exception;

public class TokenValidationFailedException extends RuntimeException{
    public TokenValidationFailedException(String message){
        super(message);
    }
}
