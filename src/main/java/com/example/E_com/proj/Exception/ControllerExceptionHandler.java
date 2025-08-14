package com.example.E_com.proj.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchProductExistsException.class)
    public ResponseEntity<ErrorMessage> productNotFoundException(NoSuchProductExistsException ex , WebRequest webRequest){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<ErrorMessage>(message,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> productAlreadyExistsException(ProductAlreadyExistsException e,WebRequest webRequest){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)

        );

        return new ResponseEntity<ErrorMessage>(message,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
