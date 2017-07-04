package com.services.exceptions;

public class InvalidEventException extends RuntimeException {

    public InvalidEventException(String message){
        super(message);
    }
}
