package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "keywords", schema = "question_bank_chatbot")
public class Keyword {
    @Id
    @Column(name = "keyword_id", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "keyword", nullable = false, length = 150)
    private String keyword;

    @ManyToMany(mappedBy = "keywords")
    private Set<Faq> faqs = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Set<Faq> getFaqs() {
        return faqs;
    }

    public void setFaqs(Set<Faq> faqs) {
        this.faqs = faqs;
    }

}