package com.sparta.financialadvisorchatbot.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConversationIdTest {

    private ConversationId conversationId;
    private ConversationHistory conversationHistory;
    private ConversationHistory conversationHistory2;


    @BeforeEach
    void setUp() {

        conversationHistory = new ConversationHistory();
        conversationHistory2 = new ConversationHistory();

        Set<ConversationHistory> conversationHistorySet = new HashSet<>(Set.of(conversationHistory, conversationHistory2));

        conversationId = new ConversationId();
        conversationId.setId(1);
        conversationId.setConversationHistories(conversationHistorySet);
    }

    @Test
    void getId() {
        Integer expectedId = 1;
        Integer actualId = conversationId.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    void getConversationHistories(){
        Set<ConversationHistory> expectedHistories = new HashSet<>(Set.of(conversationHistory, conversationHistory2));
        Set<ConversationHistory> actualHistories = conversationId.getConversationHistories();
        assertEquals(expectedHistories, actualHistories);
    }
}
