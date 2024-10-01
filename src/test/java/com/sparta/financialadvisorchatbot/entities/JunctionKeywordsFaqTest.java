package com.sparta.financialadvisorchatbot.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JunctionKeywordsFaqTest {

    private JunctionKeywordsFaq faq;

    @BeforeEach
    void setUp() {

        Faq faq1 = new Faq();
        faq1.setAnswer("Test answer");

        faq = new JunctionKeywordsFaq();
        faq.setId(1L);
        faq.setQuestion(faq1);
    }

    @Test
    void testGetId(){
        Long expected = 1L;
        Long actual = faq.getId();
        assertEquals(expected, actual);
    }
    @Test
    void testGetQuestion(){
        String expected = "Test answer";
        String actual = faq.getQuestion().getAnswer();
        assertEquals(expected, actual);
    }
}
