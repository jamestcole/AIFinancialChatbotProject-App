package com.sparta.financialadvisorchatbot.service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {

    private static final int MAX_INPUT_LENGTH = 300;
    private static final String[] DISALLOWED_PHRASES = { //PROMPT ENGINEERING
            "prompt", "prompts", "ignore last", "ignore previous instructions", "forget previous context",
            "change personality", "act as", "respond as", "disregard all instructions", "pretend to be",
            //API
            "api_key","call API",
            //GENERIC
            "password", "confidential"
            //todo more exceptions, brainstorm?
    };

    public static String validateInput(String input) {
        if(input == null || input.trim().isEmpty()){
            return "please tell me I have not entered any text, and politely ask me to try again";
        }
        else if(isOverMaxLength(input)){
            return "please tell me I have entered too many characters in my question, and politely ask me to try again";
        }
        else if(isContainingIllegalQuestion(input)){
            return "please tell me I have sent an illegal phrase, and politely ask me to try again";
        }
        else{
            return input;
        }
    }

    private static boolean isOverMaxLength(String input) {
        return input.length() > MAX_INPUT_LENGTH;
    }

    private static boolean isContainingIllegalQuestion(String input){
        String patternString = "(" + String.join("|", DISALLOWED_PHRASES) + ")";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
