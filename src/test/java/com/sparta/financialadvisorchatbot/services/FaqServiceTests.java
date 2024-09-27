package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.entities.Question;
import com.sparta.financialadvisorchatbot.repositories.QuestionRepository;
import com.sparta.financialadvisorchatbot.service.FaqService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FaqServiceTests {
    @Autowired
    private FaqService faqService;

    // Setup method, if needed to initialize data
    @BeforeEach
    public void setUp() {
        // Optional: Add setup steps if necessary
    }

    // Test: Searching for the most common keyword
    @Test
    public void testFindMostCommonKeyword() {
        String query = "Tell me about savings accounts and savings";

        String result = faqService.findMostCommonKeyword(query);

        assertEquals("savings", result, "The most common word should be 'savings'.");
    }

    // Test: Fetching FAQs by keyword (Integration test with DB)
    @Test
    public void testGetFAQsByKeyword_found() {
        String keyword = "savings";

        List<Question> faqs = faqService.getFAQsByKeyword(keyword);

        assertEquals(2, faqs.size(), "Should return 2 FAQs for the keyword 'savings'.");
        assertEquals("What is a savings account?", faqs.get(0).getQuestion());
        assertEquals("What is the difference between a checking and a savings account?", faqs.get(1).getQuestion());
    }

    // Test: Fetching FAQs when no results
    @Test
    public void testGetFAQsByKeyword_notFound() {
        String keyword = "nonexistent";

        List<Question> faqs = faqService.getFAQsByKeyword(keyword);

        assertTrue(faqs.isEmpty(), "No FAQs should be found for the keyword 'nonexistent'.");
    }

    // Test: Limiting to 3 FAQs
    @Test
    public void testGetFAQsByKeyword_limitToThree() {
        String keyword = "savings";

        List<Question> faqs = faqService.getFAQsByKeyword(keyword);

        assertTrue(faqs.size() <= 3, "The result should be limited to 3 FAQs.");
    }
}
