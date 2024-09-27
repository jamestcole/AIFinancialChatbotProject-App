package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.service.ConversationService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final ConversationService conversationService;

    public ApiController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/chatbot/{id}")
    public String postHome(@PathParam("id") Integer id, @RequestAttribute("input") String input, Model model) {
        model.addAttribute("Conversation", conversationService.getConversation(id));
        model.addAttribute("User", input);
        model.addAttribute("ChatBot", conversationService.getFaqResponse(input));
        return "redirect:/chatbot/" + id;
    }

}
