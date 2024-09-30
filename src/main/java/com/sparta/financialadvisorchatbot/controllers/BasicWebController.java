package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.service.ConversationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class BasicWebController {

    private final ConversationService conversationService;

    @Autowired
    public BasicWebController(ConversationService conversationService) {
        this.conversationService = conversationService;
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

        String faqResponse = conversationService.handleFaq(userInput);
        if (faqResponse != null) {
            botResponse = faqResponse;
        } else {
            try {
                botResponse = conversationService.generateGptResponse(userInput);
            } catch (Exception e) {
                botResponse = "Please keep your questions related to financial advice.";
            }
        }

        conversationService.saveConversationHistory(conversationId, userInput, botResponse);
        List<ConversationHistory> conversationHistory = conversationService.getConversationHistory(conversationId);
        model.addAttribute("conversationHistory", conversationHistory);
        model.addAttribute("botPrompt", "Did this help answer your question?");

        return "home";
    }
}
