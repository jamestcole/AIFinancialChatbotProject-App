package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Integer> {
}
