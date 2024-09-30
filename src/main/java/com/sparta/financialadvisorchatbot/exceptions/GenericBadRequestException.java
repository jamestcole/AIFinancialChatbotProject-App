package com.sparta.financialadvisorchatbot.exceptions;

public class GenericBadRequestException extends RuntimeException {
    public GenericBadRequestException(String message) {
        super(message);
    }
}
