package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.service.ConversationService;
import com.sparta.financialadvisorchatbot.service.FaqService;
import com.sparta.financialadvisorchatbot.service.FinancialAdvisorService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class BasicWebController {

    private final FinancialAdvisorService financialAdvisorService;
    private final ConversationService conversationService;
    private final FaqService faqService;

    public BasicWebController(FinancialAdvisorService financialAdvisorService, ConversationService conversationService, FaqService faqService) {
        this.financialAdvisorService = financialAdvisorService;
        this.conversationService = conversationService;
        this.faqService = faqService;
    }

    @GetMapping("/chatbot")
    public String getHome(Model model) {
        int newConversationId = conversationService.getLatestConversationId() + 1;
        return "redirect:/chatbot/" + newConversationId;
    }

    @GetMapping("/chatbot/{id}")
    public String getChatbotConversation(@PathParam("id") Integer id, Model model) {
        model.addAttribute("Conversation", conversationService.getConversationById(id));
        model.addAttribute("ChatBot", conversationService.getStartMessage());
        model.addAttribute("User", "");
        return "home";
    }

    @PostMapping("/chatbot/{id}")
    public String postHome(@PathParam("id") Integer id, @RequestAttribute("input") String input, Model model) {
        model.addAttribute("Conversation", conversationService.getConversationById(id));
        model.addAttribute("User", input);
        model.addAttribute("ChatBot", faqService.findClosestFaq(input));
        return "redirect:/chatbot/" + id;
    }
}
