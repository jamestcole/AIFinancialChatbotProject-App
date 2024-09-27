package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Integer> {
    List<ConversationHistory> findByConversation_ConversationId(Integer conversationId);

}
