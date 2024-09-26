package com.sparta.financialadvisorchatbot.exceptions;

public record ErrorResponse(Object errorDetails, String errorCode, String url) {
}
