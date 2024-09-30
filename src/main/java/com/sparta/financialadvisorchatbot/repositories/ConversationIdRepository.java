package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.ConversationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConversationIdRepository extends JpaRepository<ConversationId, Integer> {
    Page<ConversationId> findAll(Pageable pageable);
    Page<ConversationId> findByConversationHistoriesInputContainsIgnoringCase(Pageable pageable, String conversationHistoriesInput);
    Page<ConversationId> findByConversationHistoriesIdCreatedAtBetween(Pageable pageable, LocalDateTime from, LocalDateTime to);
}
