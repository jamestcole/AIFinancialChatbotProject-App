package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.Question;
import com.sparta.financialadvisorchatbot.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FaqService {

    private final QuestionRepository questionRepository;

    public FaqService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public ArrayList<Question> getFAQs(String input) {
        ArrayList<Question> questions = new ArrayList<>(questionRepository.findAll());
        String[] words = input.split(" ");

        ArrayList<Question> result = new ArrayList<>();

        for (Question question : questions) {
            if (Arrays.stream(words).anyMatch(question.getKeyword()::contains)) {
                result.add(question);
            }
        }

        return result.stream().limit(3).collect(Collectors.toCollection(ArrayList::new));
    }
}
