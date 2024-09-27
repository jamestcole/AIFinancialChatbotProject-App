package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.Faq;
import com.sparta.financialadvisorchatbot.repositories.FaqRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FaqService {

    private final FaqRepository questionRepository;

    public FaqService(FaqRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public ArrayList<Faq> getFAQs(String input) {
        ArrayList<Faq> questions = new ArrayList<>(questionRepository.findAll());
        String[] words = input.split(" ");

        ArrayList<Faq> result = new ArrayList<>();

        for (Faq question : questions) {
            if (Arrays.stream(words).anyMatch(question.getKeywords()::contains)) {
                result.add(question);
            }
        }

        return result.stream().limit(3).collect(Collectors.toCollection(ArrayList::new));
    }
}
