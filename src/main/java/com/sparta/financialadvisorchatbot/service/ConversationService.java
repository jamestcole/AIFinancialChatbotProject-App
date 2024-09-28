package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationHistoryId;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.entities.Faq;
import com.sparta.financialadvisorchatbot.repositories.ConversationHistoryRepository;
import com.sparta.financialadvisorchatbot.repositories.ConversationIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    private static final Logger log = LoggerFactory.getLogger(ConversationService.class);
    private final ConversationIdRepository conversationIdRepository;
    private final ConversationHistoryRepository conversationHistoryRepository;
    private final FaqService faqService;

    @Autowired
    public ConversationService(ConversationIdRepository conversationIdRepository, ConversationHistoryRepository conversationHistoryRepository, FaqService faqService) {
        this.conversationIdRepository = conversationIdRepository;
        this.conversationHistoryRepository = conversationHistoryRepository;
        this.faqService = faqService;
    }

    public ConversationId startConversation() {
        ConversationId conversationId = new ConversationId();
        return conversationIdRepository.save(conversationId);
    }

    public void saveConversationHistory(Integer conversationId, String userInput, String botResponse) {
        ConversationId conversation = conversationIdRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found."));

        ConversationHistoryId historyId = new ConversationHistoryId();
        historyId.setConversationId(conversationId);
        historyId.setCreatedAt(LocalDateTime.now());

        ConversationHistory conversationHistory = new ConversationHistory();
        conversationHistory.setId(historyId);
        conversationHistory.setConversation(conversation);
        conversationHistory.setInput(userInput);
        conversationHistory.setResponse(botResponse);

        conversationHistoryRepository.save(conversationHistory);
    }


    public List<ConversationHistory> getConversationHistory(Integer conversationId) {
        List<ConversationHistory> conversationHistory = conversationHistoryRepository.findByConversation_Id(conversationId);
        if (conversationHistory.isEmpty()) {
            throw new IllegalArgumentException("No conversation history found for this ID.");
        }
        return conversationHistory;
    }

    public String getStartMessage() {
        return "Hello, I am Sparta Global's Financial Advisor Chatbot! How can I help you today?";
    }

    public String handleUserInput(Integer conversationId, String userInput) {
        // Check for FAQ match
        String faqResponse = checkForFAQMatch(userInput);
        if (faqResponse != null) {


            ArrayList<Faq> faqs = faqService.getFAQs(userInput);
            if (!faqs.isEmpty()) {
                for(Faq faq : faqs) {

                }
            }
            return faqResponse; // Return the FAQ response
        } else {
            // If no match, prompt for more specifics
            return "Could you please be a little more specific?";
        }
    }

    private String checkForFAQMatch(String userInput) {
        return "faq response";
    }

}
