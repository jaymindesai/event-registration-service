package com.services.application.handler;

import com.services.exceptions.InvalidEventException;
import com.services.exceptions.UnregisteredUserException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    public void processValidationError(Exception exception, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UnregisteredUserException.class)
    public void processUnregisteredUserError(Exception exception, HttpServletResponse response) throws IOException {
        response.sendError(NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(InvalidEventException.class)
    public void processInvalidEventError(Exception exception, HttpServletResponse response) throws IOException {
        response.sendError(NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void processGenericError(Exception exception, HttpServletResponse response) throws IOException {
        response.sendError(INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
