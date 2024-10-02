package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.*;
import com.sparta.financialadvisorchatbot.repositories.ConversationHistoryRepository;
import com.sparta.financialadvisorchatbot.repositories.ConversationIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConversationService {

    private final ConversationIdRepository conversationIdRepository;
    private final ConversationHistoryRepository conversationHistoryRepository;
    private final FaqService faqService;
    private final OpenAiService openAiService;
    private Integer currentConversationId;

    @Autowired
    public ConversationService(ConversationIdRepository conversationIdRepository, ConversationHistoryRepository conversationHistoryRepository, FaqService faqService, OpenAiService openAiService) {
        this.conversationIdRepository = conversationIdRepository;
        this.conversationHistoryRepository = conversationHistoryRepository;
        this.faqService = faqService;
        this.openAiService = openAiService;
    }

    public Integer startConversation() {
        ConversationId conversationId = new ConversationId();
        conversationIdRepository.save(conversationId);
        currentConversationId = conversationId.getId();
        return currentConversationId;
    }

//    public Integer getCurrentConversationId() {
//        return currentConversationId;
//    }

    public void saveConversationHistory(Integer conversationId, String userInput, String botResponse) {
        ConversationHistory conversationHistory = new ConversationHistory();
        ConversationHistoryId conversationHistoryId = new ConversationHistoryId();
        conversationHistoryId.setConversationId(conversationId);
        conversationHistoryId.setCreatedAt(LocalDateTime.now());

        Optional<ConversationId> conversation = conversationIdRepository.findById(conversationId);
        if (conversation.isPresent()) {
            conversationHistory.setId(conversationHistoryId);
            conversationHistory.setConversation(conversation.get());
            conversationHistory.setInput(userInput);
            conversationHistory.setResponse(botResponse);

            conversationHistoryRepository.save(conversationHistory);
        }
    }

    public List<ConversationHistory> getConversationHistory(Integer conversationId) {
        List<ConversationHistory> conversationHistory = conversationHistoryRepository.findByConversation_Id(conversationId);
//        if (conversationHistory.isEmpty()) {
//            throw new IllegalArgumentException("No conversation history found for this ID.");
//        }
        return conversationHistory;
    }

    public String getStartMessage() {
        return "Hello, I am Sparta Global's Financial Advisor Chatbot! How can I help you today?";
    }

    public String handleFaq(String userInput) {
        Optional<Faq> faqOptional = Optional.ofNullable(faqService.getMostRelevantFaq(userInput));
        return faqOptional.map(Faq::getAnswer).orElse(null);
    }

    public String getQuestion(String userInput) {
        Optional<Faq> faqOptional = Optional.ofNullable(faqService.getMostRelevantFaq(userInput));
        return faqOptional.map(Faq::getQuestion).orElse(null);
    }

    public String getSourceUrl(String userInput) {
        Optional<Faq> faqOptional = Optional.ofNullable(faqService.getMostRelevantFaq(userInput));
        return faqOptional.map(faq -> Objects.requireNonNull(faq.getSources().stream().reduce((a, b) -> a).orElse(null)).getSourceLink()).orElse(null);
    }

    public String generateGptResponse(String userInput, List<ConversationHistory> messageHistory, Integer currentConversationId) {
        return openAiService.getResponse(userInput, messageHistory, currentConversationId);
    }
}
