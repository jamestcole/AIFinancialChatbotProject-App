package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.entities.Faq;
import com.sparta.financialadvisorchatbot.entities.Keyword;
import com.sparta.financialadvisorchatbot.repositories.FaqRepository;
import com.sparta.financialadvisorchatbot.service.FaqService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FaqServiceTests {

    @Mock
    private FaqRepository questionRepository;

    @InjectMocks
    private FaqService faqService;

    private List<Faq> questions;

    @BeforeEach()
    void setUp() {

        Keyword keyword1 = new Keyword();
        Keyword keyword2 = new Keyword();
        Keyword keyword3 = new Keyword();
        Keyword keyword4 = new Keyword();
        Keyword keyword5 = new Keyword();
        Keyword keyword6 = new Keyword();

        keyword1.setId(1);
        keyword1.setKeyword("money");
        keyword2.setId(2);
        keyword2.setKeyword("stocks");
        keyword3.setId(3);
        keyword3.setKeyword("card");
        keyword4.setId(4);
        keyword4.setKeyword("credit");
        keyword5.setId(5);
        keyword5.setKeyword("debit");
        keyword6.setId(6);
        keyword6.setKeyword("account");

        Faq question1 = new Faq();
        Faq question2 = new Faq();
        Faq question3 = new Faq();
        Faq question4 = new Faq();
        Faq question5 = new Faq();

        question1.setQuestion("How can I save money?");
        question2.setQuestion("can you tell me about stocks?");
        question3.setQuestion("What is the best card to save money with?");
        question4.setQuestion("What is the difference between a debit and credit card?");
        question5.setQuestion("What is the difference between a current and savings account?");

        question1.setKeywords(Set.of(keyword1));
        question2.setKeywords(Set.of(keyword1,keyword2));
        question3.setKeywords(Set.of(keyword3,keyword1));
        question4.setKeywords(Set.of(keyword1,keyword3,keyword4,keyword5));
        question5.setKeywords(Set.of(keyword6,keyword1));

        question1.setAnswer("by saving money");
        question2.setAnswer("by saving more money");
        question3.setAnswer("by saving even more money");
        question4.setAnswer("credit cards are cards where you don't have money, debit cards you do.");
        question5.setAnswer("by saving even more money than that");

        question1.setId(1);
        question2.setId(2);
        question3.setId(3);
        question4.setId(4);
        question5.setId(5);
        questions = new ArrayList<>(List.of(question1, question2, question3, question4));
    }

    @Test
    void testGetFaqsReturnsOnly3FaqsWhenValidInputAndMoreThanThreeAnswers(){
        int expected = 3;
        String input = "how can I save money";
        when(questionRepository.findAll()).thenReturn(questions);
        List<Faq> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.size());
    }

    @Test
    void testGetFaqsReturnsEmptyListWhenInputDoesNotContainKeyword(){
        int expected = 0;
        String input = "what is the colour of the sky";
        when(questionRepository.findAll()).thenReturn(questions);
        List<Faq> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.size());
    }

    @Test
    void testGetFaqsReturnsOnly1AnswerWhenOnlyOneKeywordMatches(){
        int expected = 1;
        String input = "stocks";
        when(questionRepository.findAll()).thenReturn(questions);
        List<Faq> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.size());
    }

    @Test
    void testGetFaqsReturnsFaqsWhenKeywordIsWithinWords(){
        int expected = 1;
        String input = "howdoifindinformationonstocksasiwanttoinvest";
        when(questionRepository.findAll()).thenReturn(questions);
        List<Faq> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.size());
    }
    @Test
    void testGetFaqsReturnsTopFaqWhenMultipleKeywordPresent(){
        String expected ="credit cards are cards where you don't have money, debit cards you do.";
        String input = "what is the difference between a credit and debit card?";
        when(questionRepository.findAll()).thenReturn(questions);
        List<Faq> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.getFirst().getAnswer());
    }
}
