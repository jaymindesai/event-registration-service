package com.ers.handler;

import com.ers.handler.exceptions.EventRegistrationException;
import com.ers.handler.exceptions.NotFoundException;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    public void processValidationError(Exception exception, HttpServletResponse response) throws IOException {
        logException(exception);
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public void processNoRegistrationsFoundError(Exception exception, HttpServletResponse response) throws IOException {
        logException(exception);
        response.sendError(NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(EventRegistrationException.class)
    public void processRegistrationConflictError(Exception exception, HttpServletResponse response) throws IOException {
        logException(exception);
        response.sendError(CONFLICT.value(), exception.getMessage());
    }

    @ExceptionHandler({MessagingException.class, TemplateException.class})
    public void processEmailMessageCreationError(Exception exception, HttpServletResponse response) throws IOException {
        logException(exception);
        response.sendError(INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void processGenericError(Exception exception, HttpServletResponse response) throws IOException {
        logException(exception);
        response.sendError(INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    private void logException(Exception exception){
        log.error("Exception Caught:", exception);
    }
}
