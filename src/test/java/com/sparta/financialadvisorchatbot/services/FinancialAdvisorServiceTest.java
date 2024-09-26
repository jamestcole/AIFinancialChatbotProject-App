package com.sparta.financialadvisorchatbot.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinancialAdvisorServiceTest {

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testAssertionWorks(){
        boolean expected = true;
        boolean actual = true;
        Assertions.assertEquals(expected, actual);
    }
}
