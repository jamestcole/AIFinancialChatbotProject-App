package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationHistoryId;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.service.ConversationService;
import com.sparta.financialadvisorchatbot.service.api.ConversationApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/sg-financial-chatbot/v1.0")
public class ApiController {

    private final ConversationApiService conversationApiService;
    private final ConversationService conversationService;

    @Autowired
    public ApiController(ConversationApiService conversationApiService, ConversationService conversationService) {
        this.conversationApiService = conversationApiService;
        this.conversationService = conversationService;
    }

    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<EntityModel<ConversationHistory>> getIndividualRequestResponse(@PathVariable Integer id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt) {
        ConversationHistoryId conversationHistoryId = new ConversationHistoryId();
        conversationHistoryId.setConversationId(id);
        conversationHistoryId.setCreatedAt(createdAt);
        ConversationHistory conversationHistory = conversationApiService.getSingleRequestResponse(conversationHistoryId);
        return ResponseEntity.ok(EntityModel.of(conversationHistory).add(linkTo(methodOn(ApiController.class).getEntireSingleConversation(conversationHistory.getId().getConversationId())).withRel("Entire conversation link: ")));
    }

    @GetMapping("/conversations/{id}")
    public ResponseEntity<CollectionModel<EntityModel<ConversationHistory>>> getEntireSingleConversation(@PathVariable Integer id) {
        List<EntityModel<ConversationHistory>> entireConversation = conversationApiService.getEntireConversationHistoryByConversationId(id).stream().map(
                conversationHistory ->  EntityModel.of(conversationHistory).add(linkTo(methodOn(ApiController.class).getIndividualRequestResponse(conversationHistory.getId().getConversationId(), conversationHistory.getId().getCreatedAt())).withRel("Individual request/response link: "))
        ).toList();
        return ResponseEntity.ok(CollectionModel.of(entireConversation));
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationId>> getAllConversations(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        List<ConversationId> conversationIds = conversationApiService.getAllConversations(page, size).getContent();
        return ResponseEntity.ok(conversationIds);
    }
}
