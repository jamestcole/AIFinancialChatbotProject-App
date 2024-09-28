package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "conversation_history", schema = "question_bank_chatbot")
public class ConversationHistory {
    @EmbeddedId
    private ConversationHistoryId id;

    @MapsId("conversationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationId conversation;

    @Size(max = 255)
    @Column(name = "input")
    private String input;

    @Lob
    @Column(name = "response")
    private String response;

    public ConversationHistoryId getId() {
        return id;
    }

    public void setId(ConversationHistoryId id) {
        this.id = id;
    }

    public ConversationId getConversation() {
        return conversation;
    }

    public void setConversation(ConversationId conversation) {
        this.conversation = conversation;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}