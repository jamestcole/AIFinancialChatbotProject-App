package com.sparta.financialadvisorchatbot.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class KeywordTest {

    private Keyword keyword;

    @BeforeEach
    public void setUp() {
        keyword = new Keyword();

        Faq faq = new Faq();
        Faq faq2 = new Faq();

        faq.setAnswer("This is a faq");
        faq2.setAnswer("This is another faq");

        Set<Faq> faqSet = Set.of(faq, faq2);

        keyword.setId(1);
        keyword.setKeyword("money");
        keyword.setFaqs(faqSet);
    }

    @Test
    void getId() {
        Integer expected = 1;
        Integer actual = keyword.getId();
        assertEquals(expected,actual);
    }
    @Test
    void getKeyword(){
        String expected = "money";
        String actual = keyword.getKeyword();
        assertEquals(expected,actual);
    }
    @Test
    void getKeywordSet(){
        String expected = "This is a faq";
        Set<Faq> faqs = keyword.getFaqs();
        Faq actualFaq = faqs.stream().filter(f -> f.getAnswer().equals(expected)).findFirst().get();
        String actual = actualFaq.getAnswer();
        assertEquals(expected, actual);
    }
}
