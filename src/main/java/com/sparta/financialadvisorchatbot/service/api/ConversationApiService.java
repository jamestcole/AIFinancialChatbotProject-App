package com.sparta.financialadvisorchatbot.service.api;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationHistoryId;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.exceptions.GenericNotFoundError;
import com.sparta.financialadvisorchatbot.repositories.ConversationHistoryRepository;
import com.sparta.financialadvisorchatbot.repositories.ConversationIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
        return conversationHistoryRepository.findById(conversationHistoryId).orElseThrow(()-> new GenericNotFoundError("Unable to find request/response"));
    }

    public List<ConversationHistory> getEntireConversationHistoryByConversationId(Integer conversationId){
        List<ConversationHistory> conversation = conversationHistoryRepository.findByConversation_Id(conversationId);
        if(conversation.isEmpty()){
            throw new GenericNotFoundError("No conversation found with id: " + conversationId);
        }
        return conversation;
    }

    public Page<ConversationId> getAllConversations(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ConversationId> allConversations = conversationIdRepository.findAll(pageable);
        for(ConversationId conversationId : allConversations.getContent()){
            conversationId.setConversationHistories(new HashSet<>(conversationHistoryRepository.findByConversation_Id(conversationId.getId())));
        }
        if(allConversations.getContent().isEmpty()){
            throw new GenericNotFoundError("No conversations found!");
        }
        return allConversations;
    }

    public Page<ConversationId> getAllConversationsByDateRange(int page, int size, LocalDate from, LocalDate to){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        LocalDateTime fromDate = from.atStartOfDay();
        LocalDateTime toDate = to.atTime(23, 59, 59);

        Page<ConversationId> allConversations = conversationIdRepository.findByConversationHistoriesIdCreatedAtBetween(pageable,fromDate,toDate);
        for (ConversationId conversationId : allConversations.getContent()) {
            conversationId.setConversationHistories(new HashSet<>(conversationHistoryRepository.findByConversation_Id(conversationId.getId())));
        }
        if (allConversations.getContent().isEmpty()) {
            throw new GenericNotFoundError("No conversations found!");
        }
        return allConversations;
    }

    public Page<ConversationId> getAllConversationsContainingKeyword(String keyword, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ConversationId> allConversations = conversationIdRepository.findByConversationHistoriesInputContainsIgnoringCase(pageable, keyword.toLowerCase());
        for(ConversationId conversationId : allConversations.getContent()){
            conversationId.setConversationHistories(new HashSet<>(conversationHistoryRepository.findByConversation_Id(conversationId.getId())));
        }
        if(allConversations.getContent().isEmpty()){
            throw new GenericNotFoundError("No conversations found!");
        }
        return allConversations;
    }
}
