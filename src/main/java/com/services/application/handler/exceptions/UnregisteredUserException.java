package com.services.application.handler.exceptions;

public class UnregisteredUserException extends RuntimeException {

    public UnregisteredUserException(String message){
        super(message);
    }
}
