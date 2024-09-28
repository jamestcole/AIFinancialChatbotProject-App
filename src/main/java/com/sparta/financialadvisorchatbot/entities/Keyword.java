package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

}