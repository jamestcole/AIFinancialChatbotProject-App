package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.ConversationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationIdRepository extends JpaRepository<ConversationId, Integer> {
}
