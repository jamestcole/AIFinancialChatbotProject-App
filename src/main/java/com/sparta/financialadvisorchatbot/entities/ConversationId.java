package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "conversation_ids", schema = "question_bank_chatbot")
public class ConversationId {
    @Id
    @Column(name = "conversation_id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "conversation")
    private Set<ConversationHistory> conversationHistories = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<ConversationHistory> getConversationHistories() {
        return conversationHistories;
    }

    public void setConversationHistories(Set<ConversationHistory> conversationHistories) {
        this.conversationHistories = conversationHistories;
    }

}