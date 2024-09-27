package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.entities.Conversation;
import com.sparta.financialadvisorchatbot.service.ConversationService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ConversationService conversationService;

    public ApiController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/chatbot/{id}")
    public ResponseEntity<Conversation> getChatbotConversation(@PathVariable Integer id) {
        return ResponseEntity.ok(conversationService.getConversationById(id));
    }

    @PostMapping("/chatbot/{id}")
    public ResponseEntity<Map<String, String>> processInput(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String userInput = request.get("input");
        String storedInput = conversationService.storeUserInput(userInput, id);
        String response = conversationService.generateResponse(storedInput, id);
        String storedResponse = conversationService.storeResponse(response, id);

        Map<String, String> result = new HashMap<>();
        result.put("input", storedInput);
        result.put("response", storedResponse);

        return ResponseEntity.ok(result);
    }
}
