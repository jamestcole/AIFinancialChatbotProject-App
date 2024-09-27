package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.Objects;

@Embeddable
public class ConversationHistoryId implements java.io.Serializable {
    private static final long serialVersionUID = -477459374128138657L;
    @NotNull
    @Column(name = "conversation_id", nullable = false)
    private Integer conversationId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConversationHistoryId entity = (ConversationHistoryId) o;
        return Objects.equals(this.createdAt, entity.createdAt) &&
                Objects.equals(this.conversationId, entity.conversationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, conversationId);
    }

}