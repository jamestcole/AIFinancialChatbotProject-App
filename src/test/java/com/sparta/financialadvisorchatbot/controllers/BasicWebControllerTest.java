package com.sparta.financialadvisorchatbot.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BasicWebControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BasicWebController basicWebController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(basicWebController).build();
    }

    //todo tests here

    @Test
    void initTest(){
        assertTrue(true);
    }
    @Test
    void testGetHome_doesSomething(){

    }
    @Test
    void testGetChatbotConversation_doesSomething(){

    }
    @Test
    void testPostHome_doesSomething(){

    }

}
