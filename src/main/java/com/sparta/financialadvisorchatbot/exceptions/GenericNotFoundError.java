package com.sparta.financialadvisorchatbot.exceptions;

public class GenericNotFoundError extends RuntimeException{
    public GenericNotFoundError(String message) {
        super(message);
    }
}
