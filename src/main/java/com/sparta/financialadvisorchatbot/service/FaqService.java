package com.sparta.financialadvisorchatbot.service;
//
//import com.sparta.financialadvisorchatbot.entities.Faq;
//import com.sparta.financialadvisorchatbot.repositories.FaqRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class FaqService {
//
//    private final FaqRepository questionRepository;
//
//    public FaqService(FaqRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }
//
//    public ArrayList<Faq> getFAQs(String input) {
//        ArrayList<Faq> questions = new ArrayList<>(questionRepository.findAll());
//        String[] words = input.split(" ");
//
//        ArrayList<Faq> result = new ArrayList<>();
//
//        for (Faq question : questions) {
//            if (Arrays.stream(words).anyMatch(question.getKeywords()::contains)) {
//                result.add(question);
//            }
//        }
//
//        return result.stream().limit(3).collect(Collectors.toCollection(ArrayList::new));
//    }
//}

import com.sparta.financialadvisorchatbot.entities.Faq;
import com.sparta.financialadvisorchatbot.entities.Keyword;
import com.sparta.financialadvisorchatbot.repositories.FaqRepository;
import com.sparta.financialadvisorchatbot.repositories.KeywordRepository;
import com.sparta.financialadvisorchatbot.repositories.SourceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FaqService {

    private final FaqRepository faqRepository;
    private final KeywordRepository keywordRepository;
    private final SourceRepository sourceRepository;

    public FaqService(FaqRepository faqRepository, KeywordRepository keywordRepository, SourceRepository sourceRepository) {
        this.faqRepository = faqRepository;
        this.keywordRepository = keywordRepository;
        this.sourceRepository = sourceRepository;
    }

    public List<Faq> findMatchingFaqs(String userInput) {
        String[] words = userInput.split(" ");
        List<Keyword> keywords = keywordRepository.findAll();
        ArrayList<Keyword> matchedKeywords = new ArrayList<>();

        for (Keyword keyword : keywords) {
            if (Arrays.stream(words).anyMatch(keyword.getKeyword()::contains)) {
                matchedKeywords.add(keyword);
            }
        }

        ArrayList<Faq> matchedFaqs = new ArrayList<>();

        for (Keyword keyword : matchedKeywords) {
            matchedFaqs.addAll(keyword.getFaqs());
        }

        return matchedFaqs;
    }

}
