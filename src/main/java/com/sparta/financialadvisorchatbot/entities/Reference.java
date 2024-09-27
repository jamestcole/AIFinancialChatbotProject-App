package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "reference", schema = "financialfaq")
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reference_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "reference", nullable = false)
    private String reference;

    @Size(max = 255)
    @Column(name = "link")
    private String link;

//    @ManyToMany(mappedBy = "reference")
//    private Set<Question> questions = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public Set<Question> getQuestions() {
//        return questions;
//    }
//
//    public void setQuestions(Set<Question> questions) {
//        this.questions = questions;
//    }

}