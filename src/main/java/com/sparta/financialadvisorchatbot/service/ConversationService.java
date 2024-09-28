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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private static final Logger log = LoggerFactory.getLogger(ConversationService.class);
    private final ConversationIdRepository conversationIdRepository;
    private final ConversationHistoryRepository conversationHistoryRepository;
    private final FaqService faqService;
    private final OpenAiService openAiService;
    // Singleton instance of the conversation
    private ConversationId activeConversation;
    //Track tier of conversation
    private boolean waitingForClarification = false;

    @Autowired
    public ConversationService(ConversationIdRepository conversationIdRepository, ConversationHistoryRepository conversationHistoryRepository, FaqService faqService, OpenAiService openAiService) {
        this.conversationIdRepository = conversationIdRepository;
        this.conversationHistoryRepository = conversationHistoryRepository;
        this.faqService = faqService;
        this.openAiService = openAiService;
    }

//    public ConversationId startConversation() {
//        ConversationId conversation = new ConversationId();
//        return conversationIdRepository.save(conversation);
//    }

    // Singleton method for creating or retrieving the active conversation
    public synchronized ConversationId startConversation() {
        // Check if there's already an active conversation
        if (activeConversation == null) {
            // Create and store the active conversation in the repository
            activeConversation = new ConversationId();
            activeConversation = conversationIdRepository.save(activeConversation);
        }
        return activeConversation; // Return the active conversation
    }

    // Optional: Method to end the current conversation and reset the singleton
    public synchronized void endConversation() {
        activeConversation = null; // Reset the active conversation
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

    public List<String> handleUserInput(Integer conversationId, String userInput) {

        if (waitingForClarification) {
            waitingForClarification = false;

//            String openAiResponse = openAiService.getChatResponse(userInput);
            String openAiResponse = "";
            return List.of(openAiResponse);
        }



        // Check for FAQ match
        List<Faq> faqResponse = checkForFAQMatch(userInput);
        if (faqResponse != null && !faqResponse.isEmpty()) {
            return faqResponse.stream()
                    .map(Faq::getAnswer) // Replace 'getAnswer' with the appropriate method from Faq
                    .collect(Collectors.toList());
        } else {
            // If no match, return a list with a single string prompting for more specifics
            waitingForClarification = true;
            return List.of("Could you please be a little more specific?");
        }
    }


    private List<Faq> checkForFAQMatch(String userInput) {
        return faqService.getFAQs(userInput);
    }

}
