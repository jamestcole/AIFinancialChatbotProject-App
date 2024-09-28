package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.entities.Question;
import com.sparta.financialadvisorchatbot.repositories.QuestionRepository;
import com.sparta.financialadvisorchatbot.service.FaqService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FaqServiceTests {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private FaqService faqService;

    private List<Question> questions;

    @BeforeEach()
    void setUp() {

        Question question1 = new Question();
        Question question2 = new Question();
        Question question3 = new Question();
        Question question4 = new Question();
        Question question5 = new Question();

        question1.setKeyword("money");
        question2.setKeyword("money");
        question3.setKeyword("money");
        question4.setKeyword("stocks");
        question5.setKeyword("money");

        question1.setAnswer("by saving money");
        question2.setAnswer("by saving more money");
        question3.setAnswer("by saving even more money");
        question4.setAnswer("stocks are a medieval device to put bad people in");
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
        List<Question> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.size());
    }

    @Test
    void testGetFaqsReturnsEmptyListWhenInputDoesNotContainKeyword(){
        int expected = 0;
        String input = "what is the colour of the sky";
        when(questionRepository.findAll()).thenReturn(questions);
        List<Question> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.size());
    }

    @Test
    void testGetFaqsReturnsOnly1AnswerWhenOnlyOneKeywordMatches(){
        int expected = 1;
        String input = "stocks";
        when(questionRepository.findAll()).thenReturn(questions);
        List<Question> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.size());
    }

    @Test
    void testGetFaqsReturnsFaqsWhenKeywordIsWithinWords(){
        int expected = 1;
        String input = "howdoifindinformationonstocksasiwanttoinvest";
        when(questionRepository.findAll()).thenReturn(questions);
        List<Question> faqs = faqService.getFAQs(input);
        assertEquals(expected, faqs.size());
    }
}
