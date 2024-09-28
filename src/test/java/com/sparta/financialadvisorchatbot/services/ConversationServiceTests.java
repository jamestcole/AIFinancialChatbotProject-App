package com.sparta.financialadvisorchatbot.services;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.repositories.ConversationHistoryRepository;
import com.sparta.financialadvisorchatbot.repositories.ConversationIdRepository;
import com.sparta.financialadvisorchatbot.service.ConversationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ConversationServiceTests {

    @Mock
    private ConversationIdRepository conversationIdRepository;

    @Mock
    private ConversationHistoryRepository conversationHistoryRepository;

    @InjectMocks
    private ConversationService conversationService;

    private ConversationId conversationId;

    @BeforeEach
    void setUp() {
        conversationId = new ConversationId();
        conversationId.setId(1);
    }

    @Test
    void startConversation_ShouldReturnSavedConversationId() {
        when(conversationIdRepository.save(any(ConversationId.class))).thenReturn(conversationId);

        ConversationId result = conversationService.startConversation();

        assertNotNull(result);
        assertEquals(conversationId.getId(), result.getId());
        verify(conversationIdRepository, times(1)).save(any(ConversationId.class));
    }

    @Test
    void saveConversationHistory_ShouldSaveHistory_WhenConversationExists() {
        when(conversationIdRepository.findById(1)).thenReturn(Optional.of(conversationId));

        String userInput = "What is investment?";
        String botResponse = "Investment is...";

        conversationService.saveConversationHistory(1, userInput, botResponse);

        ConversationHistory conversationHistory = new ConversationHistory();
        conversationHistory.setConversation(conversationId);
        conversationHistory.setInput(userInput);
        conversationHistory.setResponse(botResponse);
        conversationHistory.setCreatedAt(LocalDateTime.now());

        verify(conversationHistoryRepository, times(1)).save(any(ConversationHistory.class));
    }

    @Test
    void saveConversationHistory_ShouldThrowException_WhenConversationNotFound() {
        when(conversationIdRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            conversationService.saveConversationHistory(1, "input", "response");
        });

        assertEquals("Conversation not found.", thrown.getMessage());
    }

    @Test
    void getStartMessage_ShouldReturnWelcomeMessage() {
        String message = conversationService.getStartMessage();

        assertEquals("Hello, I am Sparta Global's Financial Advisor Chatbot! How can I help you today?", message);
    }

    @Test
    public void testGetConversationHistory_Exists() {
        List<ConversationHistory> historyList = new ArrayList<>();
        ConversationHistory conversationHistory = new ConversationHistory();
        conversationHistory.setConversation(conversationId);
        conversationHistory.setInput("User input");
        conversationHistory.setResponse("Bot response");
        historyList.add(conversationHistory);

        when(conversationHistoryRepository.findByConversation_ConversationId(1)).thenReturn(historyList);

        List<ConversationHistory> result = conversationService.getConversationHistory(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("User input", result.getFirst().getInput());
        assertEquals("Bot response", result.getFirst().getResponse());
    }

    @Test
    public void testGetConversationHistory_NotFound() {
        when(conversationHistoryRepository.findByConversation_ConversationId(1)).thenReturn(new ArrayList<>());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            conversationService.getConversationHistory(1);
        });

        assertEquals("No conversation history found for this ID.", thrown.getMessage());
    }
}