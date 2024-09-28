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



import com.sparta.financialadvisorchatbot.entities.Faq;
import com.sparta.financialadvisorchatbot.entities.Keyword;
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

        Map<Faq, Integer> topHits = new HashMap<>();

        for(Faq faq : questions){
            Set<Keyword> keywords = faq.getKeywords();
            for (Keyword keyword : keywords) {
                if (input.toLowerCase().contains(keyword.getKeyword().toLowerCase())) {
                    if(topHits.containsKey(faq)){
                        topHits.put(faq, topHits.get(faq) + 1);
                    }
                    else topHits.put(faq, 1);
                }
            }
        }

        return topHits
                .entrySet()
                .stream()
                .sorted((entry1, entry2)-> entry2.getValue().compareTo(entry1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));

    }
}
