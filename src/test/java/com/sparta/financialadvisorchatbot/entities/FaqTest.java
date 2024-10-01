package com.sparta.financialadvisorchatbot.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FaqTest {

    private Faq faq;

    private Keyword keyword1;
    private Keyword keyword2;

    @BeforeEach
    void setUp() {

        Source source1 = new Source();
        Source source2 = new Source();

        source1.setId(1);
        source1.setSourceLink("https://www.example1.com");
        source1.setSourceTitle("example title");

        source2.setId(2);
        source2.setSourceLink("https://www.example2.com");
        source2.setSourceTitle("example title 2");

        Set<Source> sources = Set.of(source1, source2);

        keyword1 = new Keyword();
        keyword2 = new Keyword();

        keyword1.setId(1);
        keyword1.setKeyword("money");


        keyword2.setId(1);
        keyword2.setKeyword("stock");

        Set<Keyword> keywords = new HashSet<>();
        keywords.add(keyword1);
        keywords.add(keyword2);

        faq = new Faq();
        faq.setQuestion("How do I save money");
        faq.setAnswer("By doing...");
        faq.setId(1);
        faq.setKeywords(keywords);
        faq.setSources(sources);
    }

    @Test
    void testGetIdReturnsId(){
        Integer expected = 1;
        Integer actual = faq.getId();
        assertEquals(expected, actual);
    }

    @Test
    void testGetQuestionReturnsQuestion(){
        String expected = "How do I save money";
        String actual = faq.getQuestion();
        assertEquals(expected,actual);
    }
    @Test
    void testGetAnswerReturnsAnswer(){
        String expected = "By doing...";
        String actual = faq.getAnswer();
        assertEquals(expected, actual);
    }
    @Test
    void testGetKeywordsReturnsKeywords(){
        String expected = "money";
        Set<Keyword> keywords = faq.getKeywords();
        Keyword actualKeyword = keywords.stream().filter(keyword -> keyword.getId() == 1).findFirst().get();
        String actual = actualKeyword.getKeyword();
        assertEquals(expected,actual);
    }
    @Test
    void testGetSourcesReturnsSources(){
        String expected = "https://www.example1.com";
        Set<Source> sources = faq.getSources();
        Source actualSource = sources.stream().filter(source->source.getId()==1).findFirst().get();
        String actual = actualSource.getSourceLink();
        assertEquals(expected, actual);
    }
}
