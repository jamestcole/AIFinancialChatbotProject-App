package com.sparta.financialadvisorchatbot.exceptions;

public class ResponseParsingError extends RuntimeException{
    public ResponseParsingError(String message) {
        super(message);
    }
}
