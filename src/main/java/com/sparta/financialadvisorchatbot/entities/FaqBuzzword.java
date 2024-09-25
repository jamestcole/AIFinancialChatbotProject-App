package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "faq_buzzwords", schema = "financialfaq")
public class FaqBuzzword {
    @EmbeddedId
    private FaqBuzzwordId id;

    @MapsId("faqId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FAQ_ID", nullable = false)
    private com.sparta.financialadvisorchatbot.entities.Faq faq;

    @MapsId("buzzwordId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Buzzword_ID", nullable = false)
    private Buzzword buzzword;

    public FaqBuzzwordId getId() {
        return id;
    }

    public void setId(FaqBuzzwordId id) {
        this.id = id;
    }

    public com.sparta.financialadvisorchatbot.entities.Faq getFaq() {
        return faq;
    }

    public void setFaq(com.sparta.financialadvisorchatbot.entities.Faq faq) {
        this.faq = faq;
    }

    public Buzzword getBuzzword() {
        return buzzword;
    }

    public void setBuzzword(Buzzword buzzword) {
        this.buzzword = buzzword;
    }

}