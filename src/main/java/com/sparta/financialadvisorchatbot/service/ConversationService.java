package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationHistoryId;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.repositories.ConversationHistoryRepository;
import com.sparta.financialadvisorchatbot.repositories.ConversationIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ConversationService {

    private final ConversationIdRepository conversationIdRepository;
    private final ConversationHistoryRepository conversationHistoryRepository;

    @Autowired
    public ConversationService(ConversationIdRepository conversationIdRepository, ConversationHistoryRepository conversationHistoryRepository) {
        this.conversationIdRepository = conversationIdRepository;
        this.conversationHistoryRepository = conversationHistoryRepository;
    }

    public ConversationId startConversation() {
        ConversationId conversationId = new ConversationId();
        return conversationIdRepository.save(conversationId);
    }

    public void saveConversationHistory(Integer conversationId, String userInput, String botResponse) {
        ConversationId conversation = conversationIdRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found."));

        ConversationHistory conversationHistory = new ConversationHistory();
        conversationHistory.setConversation(conversation);
        conversationHistory.setInput(userInput);
        conversationHistory.setResponse(botResponse);
        conversationHistory.setCreatedAt(LocalDateTime.now());

        conversationHistoryRepository.save(conversationHistory);
    }

    public String getStartMessage() {
        return "Hello, I am Sparta Global's Financial Advisor Chatbot! How can I help you today?";
    }
}
