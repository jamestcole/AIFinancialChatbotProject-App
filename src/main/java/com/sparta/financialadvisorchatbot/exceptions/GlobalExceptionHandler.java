package com.sparta.financialadvisorchatbot.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseParsingError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleResponseParsingError(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse("CHATBOT PARSING ERROR", ex.getMessage(), request.getRequestURL().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GenericNotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleGenericNotFoundError(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse("NOT FOUND", ex.getMessage(), request.getRequestURL().toString()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleGenericBadRequestException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorResponse("BAD REQUEST", ex.getMessage(), request.getRequestURL().toString()), HttpStatus.BAD_REQUEST);
    }
}
