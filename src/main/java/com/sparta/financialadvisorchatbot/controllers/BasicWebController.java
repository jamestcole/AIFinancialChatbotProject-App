package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.service.ConversationService;
import com.sparta.financialadvisorchatbot.service.FaqService;
import com.sparta.financialadvisorchatbot.service.FinancialAdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BasicWebController {

    private final FinancialAdvisorService financialAdvisorService;
    private final ConversationService conversationService;
    private final FaqService faqService;

    @Autowired
    public BasicWebController(FinancialAdvisorService financialAdvisorService, ConversationService conversationService, FaqService faqService) {
        this.financialAdvisorService = financialAdvisorService;
        this.conversationService = conversationService;
        this.faqService = faqService;
    }

    @GetMapping("/chatbot")
    public String getHome(Model model) {
        model.addAttribute("startMessage", conversationService.getStartMessage());
        return "home";
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestParam("userInput") String userInput, Model model) {
        ConversationId conversationId = conversationService.startConversation();

        String botResponse = conversationService.handleUserInput(conversationId.getId(), userInput);

        conversationService.saveConversationHistory(conversationId.getId(), userInput, botResponse);

        List<ConversationHistory> conversationHistory = conversationService.getConversationHistory(conversationId.getId());
        model.addAttribute("conversationHistory", conversationHistory);
        model.addAttribute("currentYear", LocalDate.now().getYear());
        model.addAttribute("startMessage", conversationService.getStartMessage());

        return "home";
    }
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
