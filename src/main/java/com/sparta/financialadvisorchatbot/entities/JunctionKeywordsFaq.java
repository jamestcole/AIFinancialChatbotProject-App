package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "junction_keywords_faqs", schema = "question_bank_chatbot")
public class JunctionKeywordsFaq {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Faq question;
    @Id
    private Long id;

    public Faq getQuestion() {
        return question;
    }

    public void setQuestion(Faq question) {
        this.question = question;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}