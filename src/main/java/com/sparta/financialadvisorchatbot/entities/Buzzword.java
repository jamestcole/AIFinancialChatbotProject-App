package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "buzzwords", schema = "financialfaq")
public class Buzzword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Buzzword_ID", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "Buzzword", length = 50)
    private String buzzword;

    @ManyToMany
    @JoinTable(name = "faq_buzzwords",
            joinColumns = @JoinColumn(name = "Buzzword_ID"),
            inverseJoinColumns = @JoinColumn(name = "FAQ_ID"))
    private Set<com.sparta.financialadvisorchatbot.entities.Faq> faqs = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuzzword() {
        return buzzword;
    }

    public void setBuzzword(String buzzword) {
        this.buzzword = buzzword;
    }

    public Set<com.sparta.financialadvisorchatbot.entities.Faq> getFaqs() {
        return faqs;
    }

    public void setFaqs(Set<com.sparta.financialadvisorchatbot.entities.Faq> faqs) {
        this.faqs = faqs;
    }

}