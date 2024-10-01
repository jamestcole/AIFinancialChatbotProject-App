package com.sparta.financialadvisorchatbot.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SourceTest {

    private Source source;

    @BeforeEach
    void setUp() {
        source = new Source();
        source.setSourceLink("https://www.example1.com");
        source.setId(1);
        source.setSourceTitle("example title");
    }

    @Test
    void getSourceLink() {
        String expected = "https://www.example1.com";
        String actual = source.getSourceLink();
        assertEquals(expected, actual);
    }

    @Test
    void getId(){
        Integer expected = 1;
        Integer actual = source.getId();
        assertEquals(expected, actual);
    }

    @Test
    void getSourceTitle() {
        String expected = "example title";
        String actual = source.getSourceTitle();
        assertEquals(expected, actual);
    }
}
