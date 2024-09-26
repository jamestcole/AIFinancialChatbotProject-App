package com.sparta.financialadvisorchatbot.service;

import org.springframework.stereotype.Service;

@Service
public class OpenAiService {
    public static String getChatCompletion(String userInput) {
        return "gptResponse";
    }
}
