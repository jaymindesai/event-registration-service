package com.services.application.handler.exceptions;

public class InvalidEventException extends RuntimeException {

    public InvalidEventException(String message){
        super(message);
    }
}
