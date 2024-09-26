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
}
