package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.service.ConversationFormatter;
import com.sparta.financialadvisorchatbot.service.ConversationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/chat")
public class BasicWebController {

    private final ConversationService conversationService;
    private final ConversationFormatter conversationFormatter;
    private String faqQuestion;
    private String faqQuestionSource;

    @Autowired
    public BasicWebController(ConversationService conversationService, ConversationFormatter conversationFormatter) {
        this.conversationService = conversationService;
        this.conversationFormatter = conversationFormatter;
    }

    @GetMapping
    public String chat(Model model, HttpSession session) {
        // Always create a new conversation when entering the chat page
        Integer conversationId = conversationService.startConversation();
        session.setAttribute("currentConversationId", conversationId);

        // Load the new conversation history (which will be empty)
        model.addAttribute("conversationHistory", List.of());
        model.addAttribute("botPrompt", "How can I help you today?");
        return "home";
    }

    @PostMapping()
    public String handleUserMessage(@RequestParam("userInput") String userInput, HttpSession session, Model model) {
        String botResponse;
        // Retrieve the current conversation ID from the session
        Integer conversationId = (Integer) session.getAttribute("currentConversationId");
        if (conversationId == null) {
            // If no conversation exists, start a new one
            conversationId = conversationService.startConversation();
            session.setAttribute("currentConversationId", conversationId);
        }

        List<ConversationHistory> conversationHistoryList = List.of();
        Optional<List<ConversationHistory>> conversationHistoryOptional = Optional.ofNullable(conversationService.getConversationHistory(conversationId));
        if (conversationHistoryOptional.isPresent()) {
            conversationHistoryList = conversationHistoryOptional.get();
        }

        String faqResponse = conversationService.handleFaq(userInput);
        if (faqResponse != null) {

            faqQuestion = conversationService.getQuestion(userInput);
            faqQuestionSource = conversationService.getSourceUrl(userInput);
            System.out.println(faqQuestionSource);

            model.addAttribute("faqQuestion", faqQuestion);
            model.addAttribute("faqQuestionSource", faqQuestionSource);

            botResponse = faqResponse;
            System.out.println("FAQ Response: " + faqResponse);
        } else {
            try {
                botResponse = conversationService.generateGptResponse(userInput, conversationHistoryList, conversationId);
                System.out.println("GPT Response: " + botResponse);
            } catch (Exception e) {
                botResponse = "Please keep your questions related to financial advice.";
            }
        }

        conversationService.saveConversationHistory(conversationId, userInput, botResponse);
        List<ConversationHistory> conversationHistory = conversationService.getConversationHistory(conversationId);
        model.addAttribute("conversationHistory", conversationHistory);

        return "home";
    }
}
