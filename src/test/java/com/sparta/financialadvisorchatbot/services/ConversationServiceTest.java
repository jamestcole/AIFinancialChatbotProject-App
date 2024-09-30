package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.Faq;
import com.sparta.financialadvisorchatbot.repositories.ConversationHistoryRepository;
import com.sparta.financialadvisorchatbot.repositories.ConversationIdRepository;
import com.sparta.financialadvisorchatbot.service.ConversationService;
import com.sparta.financialadvisorchatbot.service.FaqService;
import com.sparta.financialadvisorchatbot.service.OpenAiService;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConversationServiceTest {

    //May also need to mock ConversationId

    @Mock
    OpenAiService openAiService;

    @Mock
    ConversationHistoryRepository conversationHistoryRepository;

    @Mock
    ConversationIdRepository conversationIdRepository;

    @Mock
    FaqService faqService;

    @InjectMocks
    ConversationService conversationService;

    private ConversationId conversationId;
    private List<ConversationHistory> conversationHistoryList;

    /** Methods to test
     ConversationHistory getLatestConversation()
     Integer getLatestConversationId()
     ConversationHistory getConversationById()
     String getStartMessage()
     ConversationHistory startNewConversation()
     void addMessageToConversation(Integer convId, String message)
     void updateConversation(int latestId, ArrayList<String> conversation)
     **/

    @BeforeEach
    void setUp() {
        conversationId = new ConversationId();
        conversationId.setId(1);

        ConversationHistory conversationHistory1 = new ConversationHistory();
        ConversationHistory conversationHistory2 = new ConversationHistory();

        conversationHistoryList = new ArrayList<>();

        conversationHistory1.setConversation(conversationId);
        conversationHistory2.setConversation(conversationId);
        conversationHistory1.setInput("hello");
        conversationHistory2.setInput("how do i save money");
        conversationHistory1.setResponse("Hello, I am Sparta Global's Financial Advisor Chatbot! How can I help you today?");
        conversationHistory2.setResponse("by opening a savings account");
        conversationHistoryList.add(conversationHistory1);
        conversationHistoryList.add(conversationHistory2);
    }

    @Test
    void testGetStartMessageReturnsStartMessage(){
        String expected = "Hello, I am Sparta Global's Financial Advisor Chatbot! How can I help you today?";
        String actual = conversationService.getStartMessage();
        assertEquals(expected, actual);
    }

    @Test
    void testStartConversationReturnsNewConversationId(){
        ConversationId conversationId = new ConversationId();
        when(conversationIdRepository.save(any(ConversationId.class))).thenReturn(conversationId);
        ConversationId actual = conversationService.startConversation();
        assertEquals(conversationId, actual);
    }

    @Test
    void testSaveConversationHistorySavesConversationHistoryIfSuccessful(){

        when(conversationIdRepository.findById(any(Integer.class))).thenReturn(Optional.of(conversationId));
        ArgumentCaptor<ConversationHistory> conversationHistoryCaptor = ArgumentCaptor.forClass(ConversationHistory.class);

        conversationService.saveConversationHistory(1,"test input", "test response");

        verify(conversationHistoryRepository, times(1)).save(conversationHistoryCaptor.capture());
        ConversationHistory capturedConversationHistory = conversationHistoryCaptor.getValue();
        assertEquals(1, capturedConversationHistory.getConversation().getId());
        assertEquals("test input", capturedConversationHistory.getInput());
        assertEquals("test response", capturedConversationHistory.getResponse());

    }
    @Test
    void testSaveConversationHistoryThrowsExceptionIfCannotFindByConversationId(){
        String expected = "Conversation not found.";
        when(conversationIdRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            conversationService.saveConversationHistory(1, "user input", "bot response");
        });
        assertEquals(expected, thrown.getMessage());
    }

    @Test
    void testGetConversationHistoryReturnsListOfConversationHistory(){
        when(conversationHistoryRepository.findByConversation_Id(any(Integer.class))).thenReturn(conversationHistoryList);
        List<ConversationHistory> actual = conversationService.getConversationHistory(1);
        assertEquals(conversationHistoryList, actual);
    }

    @Test
    void testGetConversationHistoryThrowsExceptionIfCannotFindByConversationId(){
        String expected = "No conversation history found for this ID.";
        when(conversationHistoryRepository.findByConversation_Id(any(Integer.class))).thenReturn(Collections.emptyList());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            conversationService.getConversationHistory(2);
        });
        assertEquals(expected, thrown.getMessage());
    }

//    @Test
//    void testHandleUserInputReturnsFaqResponseIfFaqResponseExists(){
//        when(faqService.getFAQs(any(String.class))).thenReturn(new ArrayList<>());
//    }

//    @Test
//    void testHandleUserInputReturnsGptResponseIfNoFaqResponseExists(){
//
//    }
}
