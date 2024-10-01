package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class DataAnalysisController {

    private final WebClient webClient;

    @Autowired
    public DataAnalysisController(final WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/history")
    public String getAllConversations(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String keyword, Model model){

        ResponseEntity<List<ConversationId>> allConversations;
        if(keyword.isEmpty()) {
            allConversations =
                    webClient
                            .get()
                            .uri("/api/sg-financial-chatbot/v1.0/conversations?page=" + page + "&size=" + size)
                            .retrieve()
                            .toEntityList(ConversationId.class)
                            .block();
        }
        else{
            allConversations = webClient
                    .get()
                    .uri("/api/sg-financial-chatbot/v1.0/conversations/containing?keyword=" + keyword + "&page=" + page + "&size=" + size)
                    .retrieve()
                    .toEntityList(ConversationId.class)
                    .block();
        }

        model.addAttribute("conversations", allConversations.getBody());
        model.addAttribute("currentPage", page);
        model.addAttribute("currentYear", java.time.Year.now().getValue());
        return "conversations";

    }

    @GetMapping("/conversations/{id}")
    public String getConversationDetails(@PathVariable("id") int id, Model model){
        ResponseEntity<List<ConversationHistory>> fullConversation =
                webClient.get().uri("/api/sg-financial-chatbot/v1.0/conversations/" + id)
                        .retrieve()
                        .toEntityList(ConversationHistory.class)
                        .block();
        model.addAttribute("conversation", fullConversation.getBody());
        return "conversation-details";
    }
}
