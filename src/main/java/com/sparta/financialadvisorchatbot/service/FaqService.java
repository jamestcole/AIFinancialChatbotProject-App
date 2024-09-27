package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class FaqService {
    public String findClosestFaq(String userInput) {
        return "Answer";
    }
}
