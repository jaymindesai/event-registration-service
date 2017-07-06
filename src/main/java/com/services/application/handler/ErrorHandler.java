package com.services.application.handler;

import com.services.application.handler.exceptions.InvalidEventException;
import com.services.application.handler.exceptions.EventRegistrationException;
import com.services.application.handler.exceptions.NotFoundException;
import com.services.application.handler.exceptions.UnregisteredUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

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

    @ExceptionHandler(NotFoundException.class)
    public void processNoRegistrationsFoundError(Exception exception, HttpServletResponse response) throws IOException {
        response.sendError(NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(EventRegistrationException.class)
    public void processRegistrationConflictError(Exception exception, HttpServletResponse response) throws IOException {
        response.sendError(CONFLICT.value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void processGenericError(Exception exception, HttpServletResponse response) throws IOException {
        response.sendError(INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
