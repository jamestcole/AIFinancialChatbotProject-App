package com.sparta.financialadvisorchatbot.entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JunctionFaqsSourceTest {

    private JunctionFaqsSource faqsSource;

    @BeforeEach
    void setup(){
        Source source = new Source();
        source.setSourceLink("https://www.example.com");
        source.setId(1);
        source.setSourceTitle("example source");

        faqsSource = new JunctionFaqsSource();
        faqsSource.setSource(source);
        faqsSource.setId(1L);
    }

    @Test
    void getSource(){
        String expected = "https://www.example.com";
        String actual = faqsSource.getSource().getSourceLink();
        assertEquals(expected, actual);
    }

    @Test
    void getId(){
        Long expected = 1L;
        Long actual = faqsSource.getId();
        assertEquals(expected, actual);
    }
}
