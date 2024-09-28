package com.sparta.financialadvisorchatbot.service.api;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationHistoryId;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.repositories.ConversationHistoryRepository;
import com.sparta.financialadvisorchatbot.repositories.ConversationIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationApiService {

    private final ConversationHistoryRepository conversationHistoryRepository;
    private final ConversationIdRepository conversationIdRepository;

    @Autowired
    public ConversationApiService(ConversationHistoryRepository conversationHistoryRepository, ConversationIdRepository conversationIdRepository) {
        this.conversationHistoryRepository = conversationHistoryRepository;
        this.conversationIdRepository = conversationIdRepository;
    }

    public ConversationHistory getSingleRequestResponse(ConversationHistoryId conversationHistoryId) {
        return conversationHistoryRepository.findById(conversationHistoryId).orElseThrow();
    }

    public List<ConversationHistory> getEntireConversationHistoryByConversationId(Integer conversationId){
        List<ConversationHistory> conversation = conversationHistoryRepository.findByConversation_Id(conversationId);
        if(conversation.isEmpty()){
            //throw 404 error;
        }
        return conversation;
    }

    public Page<ConversationId> getAllConversations(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return conversationIdRepository.findAll(pageable);
    }





}
