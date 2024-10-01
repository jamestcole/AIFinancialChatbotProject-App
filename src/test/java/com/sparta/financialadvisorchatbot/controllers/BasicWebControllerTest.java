package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.service.ConversationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BasicWebControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ConversationService conversationService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testChatGetMapping() throws Exception {
        when(conversationService.startConversation()).thenReturn(1);

        mockMvc.perform(get("/chat"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("conversationHistory", List.of()))
                .andExpect(model().attribute("botPrompt","How can I help you today?"));

        verify(conversationService, times(1)).startConversation();
        ;
    }
    @Test
    void testHandleUserMessageWithFaqResponse() throws Exception {
        when(conversationService.startConversation()).thenReturn(1);
        when(conversationService.getConversationHistory(1)).thenReturn(List.of());
        when(conversationService.handleFaq("Test message")).thenReturn("This is a FAQ response");

        mockMvc.perform(post("/chat")
                .param("userInput","Test message")
                .sessionAttr("currentConversationId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("conversationHistory", List.of()))
                .andExpect(model().attribute("botPrompt","Did this help answer your question?"));

        verify(conversationService, times(2)).getConversationHistory(1);
        verify(conversationService, times(1)).handleFaq("Test message");
        verify(conversationService, times(1)).saveConversationHistory(eq(1), eq("Test message"), eq("This is a FAQ response"));
    }

    @Test
    public void testHandleUserMessageWithGptResponse() throws Exception {
        when(conversationService.startConversation()).thenReturn(1);
        when(conversationService.getConversationHistory(1)).thenReturn(List.of());
        when(conversationService.handleFaq("Test message")).thenReturn(null);
        when(conversationService.generateGptResponse(eq("Test message"), any(List.class), eq(1))).thenReturn("This is a GPT response.");

        mockMvc.perform(post("/chat")
                        .param("userInput", "Test message")
                        .sessionAttr("currentConversationId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("conversationHistory", List.of()))
                .andExpect(model().attribute("botPrompt", "Did this help answer your question?"));


        verify(conversationService, times(2)).getConversationHistory(1);
        verify(conversationService, times(1)).handleFaq("Test message");
        verify(conversationService, times(1)).generateGptResponse(eq("Test message"), any(List.class), eq(1));
        verify(conversationService, times(1)).saveConversationHistory(eq(1), eq("Test message"), eq("This is a GPT response."));
    }
    @Test
    public void testHandleUserMessageWithException() throws Exception {
        when(conversationService.startConversation()).thenReturn(1);
        when(conversationService.getConversationHistory(1)).thenReturn(List.of());
        when(conversationService.handleFaq("Test message")).thenReturn(null);
        when(conversationService.generateGptResponse(eq("Test message"), any(List.class), eq(1)))
                .thenThrow(new RuntimeException("Exception occurred"));

        mockMvc.perform(post("/chat")
                        .param("userInput", "Test message")
                        .sessionAttr("currentConversationId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("conversationHistory", List.of()))
                .andExpect(model().attribute("botPrompt", "Did this help answer your question?"));

        verify(conversationService, times(2)).getConversationHistory(1);
        verify(conversationService, times(1)).handleFaq("Test message");
        verify(conversationService, times(1)).generateGptResponse(eq("Test message"), any(List.class), eq(1));
        verify(conversationService, times(1)).saveConversationHistory(eq(1), eq("Test message"), eq("Please keep your questions related to financial advice."));
    }
}
