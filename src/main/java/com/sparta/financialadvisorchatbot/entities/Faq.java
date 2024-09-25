package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "faqs", schema = "financialfaq")
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAQ_ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "Question")
    private String question;

    @Lob
    @Column(name = "Answer")
    private String answer;

    @ManyToMany(mappedBy = "faqs")
    private Set<Buzzword> buzzwords = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Set<Buzzword> getBuzzwords() {
        return buzzwords;
    }

    public void setBuzzwords(Set<Buzzword> buzzwords) {
        this.buzzwords = buzzwords;
    }

}