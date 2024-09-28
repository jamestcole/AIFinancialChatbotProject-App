package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.service.ConversationService;
import com.sparta.financialadvisorchatbot.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BasicWebController {


    private final ConversationService conversationService;
    private final FaqService faqService;

    @Autowired
    public BasicWebController(ConversationService conversationService, FaqService faqService) {
        this.conversationService = conversationService;
        this.faqService = faqService;
    }

    @GetMapping("/chat")
    public String chat(Model model, @SessionAttribute(required = false) Integer conversationId) {
        if (conversationId == null) {
            model.addAttribute("startMessage", conversationService.getStartMessage());
        }
        if (conversationId != null) {
            List<ConversationHistory> conversationHistory = conversationService.getConversationHistory(conversationId);
            model.addAttribute("conversationHistory", conversationHistory);

        }
        return "home";
    }

//    @PostMapping("/chat")
//    public String handleUserInput(@RequestParam String userInput, @SessionAttribute(required = false) Integer conversationId, Model model) {
//        // If conversation is not started, start a new one
//        if (conversationId == null) {
//            conversationId = conversationService.startConversation().getId();
//        }
//
//        // Save conversation history and get bot response
//        String botResponse = conversationService.handleUserInput(conversationId, userInput);
//        conversationService.saveConversationHistory(conversationId, userInput, botResponse);
//
//        // Fetch updated conversation history
//        List<ConversationHistory> conversationHistory = conversationService.getConversationHistory(conversationId);
//        model.addAttribute("conversationHistory", conversationHistory);
//        model.addAttribute("startMessage", conversationService.getStartMessage());
//
//        return "home"; // Thymeleaf template
//    }

    @PostMapping("/chat")
    public String handleUserInput(@RequestParam String userInput, @SessionAttribute(required = false) Integer conversationId, Model model) {
        if (conversationId == null) {
            conversationId = conversationService.startConversation().getId();
        }

        // Get FAQ or GPT response
        List<String> botResponse = conversationService.handleUserInput(conversationId, userInput);

        // Save conversation history (convert list to a single string for history)
        String combinedResponse = String.join("\n", botResponse); // Combine the list into one string for history saving
        conversationService.saveConversationHistory(conversationId, userInput, combinedResponse);

        // Fetch updated conversation history
        List<ConversationHistory> conversationHistory = conversationService.getConversationHistory(conversationId);
        model.addAttribute("conversationHistory", conversationHistory);
        model.addAttribute("botResponse", botResponse); // Pass the list to the frontend
        model.addAttribute("startMessage", conversationService.getStartMessage());

        return "home"; // Thymeleaf template
    }


//    @PostMapping("/chat")
//    public String askQuestion(@RequestParam("userInput") String userInput, Model model) {
//        ConversationId conversationId = conversationService.startConversation();
//
//        String botResponse = conversationService.handleUserInput(conversationId.getId(), userInput);
//
//        conversationService.saveConversationHistory(conversationId.getId(), userInput, botResponse);
//
//        List<ConversationHistory> conversationHistory = conversationService.getConversationHistory(conversationId.getId());
//        model.addAttribute("conversationHistory", conversationHistory);
//        model.addAttribute("startMessage", conversationService.getStartMessage());
//
//        return "home";
//    }
//
//    @PostMapping("/chatbot")
//    public String postHome(@RequestAttribute("input") String input, Model model) {
//        model.addAttribute("Conversation", conversationService.getConversationById(conversationService.getLatestConversationId()));
//        model.addAttribute("User", input);
//        model.addAttribute("ChatBot", faqService.getFAQs(input));
//        return "redirect:/chatbot/" + conversationService.getLatestConversationId();
//    }
//
//    @GetMapping("/chatbot/{id}")
//    public String getChatbotConversation(@PathVariable Integer id, Model model) {
//        model.addAttribute("Conversation", conversationService.getConversationById(id));
//        model.addAttribute("ChatBot", conversationService.getStartMessage());
//        model.addAttribute("User", "");
//        return "home";
//    }
//
//    @PostMapping("/chatbot/{id}")
//    public String postHome(@PathVariable Integer id, @RequestAttribute("input") String input, Model model) {
//        model.addAttribute("Conversation", conversationService.getConversationById(id));
//        model.addAttribute("User", input);
//        model.addAttribute("ChatBot", faqService.getFAQs(input));
//        return "redirect:/chatbot/" + id;
//    }
}
