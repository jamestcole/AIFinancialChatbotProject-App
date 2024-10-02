package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.Faq;
import com.sparta.financialadvisorchatbot.entities.Keyword;
import com.sparta.financialadvisorchatbot.repositories.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FaqService {


    private final FaqRepository faqRepository;

    @Autowired
    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    private static final int KEYWORD_MATCH_THRESHOLD = 2;

    public Faq getMostRelevantFaq(String userInput) {
        String normalizedInput = normalizeText(userInput);

        List<Faq> faqs = faqRepository.findAll();
        Faq bestMatch = null;
        int maxKeywordMatches = 0;

        for (Faq faq : faqs) {
            String normalizedFaqQuestion = normalizeText(faq.getQuestion());
            if (isCloseMatch(normalizedInput, normalizedFaqQuestion)) {
                return faq;
            }

            Set<Keyword> keywords = faq.getKeywords();
            int keywordMatches = countMatchingKeywords(keywords, normalizedInput);

            if (keywordMatches > maxKeywordMatches) {
                bestMatch = faq;
                maxKeywordMatches = keywordMatches;
            }
        }

        if (maxKeywordMatches < KEYWORD_MATCH_THRESHOLD) {
            return null;
        }

        return bestMatch;
    }

    private boolean isCloseMatch(String userInput, String faqQuestion) {
        return faqQuestion.equalsIgnoreCase(userInput);
    }

    private int countMatchingKeywords(Set<Keyword> keywords, String userInput) {
        int matchCount = 0;
        for (Keyword keyword : keywords) {
            String normalizedKeyword = normalizeText(keyword.getKeyword());
            if (userInput.contains(normalizedKeyword)) {
                matchCount++;
            }
        }
        return matchCount;
    }

    private String normalizeText(String text) {
        return text.toLowerCase().replaceAll("[^a-z0-9 ]", "").trim();
    }
}



