package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.Question;
import com.sparta.financialadvisorchatbot.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FaqService {
    public String findClosestFaq(String userInput) {
        return "Answer";
    }
    @Autowired
    private QuestionRepository questionRepository;


    public List<Question> findFAQ(String userInput) {
        return getFAQsByKeyword(findMostCommonKeyword(userInput));
    }

    // Method to find the most common keyword in a query
    public String findMostCommonKeyword(String query) {
        // Split the query into individual words
        String[] words = query.toLowerCase().split("\\s+");

        Map<String, Integer> keywordCount = new HashMap<>();

        // Iterate over all words and count their occurrences
        for (String word : words) {
            keywordCount.put(word, keywordCount.getOrDefault(word, 0) + 1);
        }

        // Find the most common keyword in the map
        String mostCommonKeyword = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : keywordCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommonKeyword = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostCommonKeyword;
    }

    // Method to get up to 3 FAQs based on the keyword
    public List<Question> getFAQsByKeyword(String keyword) {
        List<Question> questions = questionRepository.findByKeywordContainingIgnoreCase(keyword);

        // Return a maximum of 3 questions
        return questions.stream().limit(3).collect(Collectors.toList());
    }
}
