package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "questions", schema = "financialfaq")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Lob
    @Column(name = "answer", nullable = false)
    private String answer;

    @Size(max = 255)
    @Column(name = "keyword")
    private String keyword;

    //@ManyToMany(mappedBy = "question")
    //private Set<com.sparta.financialadvisorchatbot.entities.Reference> references = new LinkedHashSet<>();

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

//    public Set<com.sparta.financialadvisorchatbot.entities.Reference> getReferences() {
//        return references;
//    }
//
//    public void setReferences(Set<com.sparta.financialadvisorchatbot.entities.Reference> references) {
//        this.references = references;
//    }

}