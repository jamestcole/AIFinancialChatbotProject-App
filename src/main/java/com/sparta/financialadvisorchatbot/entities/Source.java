package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sources", schema = "question_bank_chatbot")
public class Source {
    @Id
    @Column(name = "source_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "source_title", nullable = false)
    private String sourceTitle;

    @Size(max = 2048)
    @NotNull
    @Column(name = "source_link", nullable = false, length = 2048)
    private String sourceLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

}