package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "junction_qr", schema = "financialfaq")
public class JunctionQr {
    @EmbeddedId
    private JunctionQrId id;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private com.sparta.financialadvisorchatbot.entities.Question question;

    @MapsId("referenceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reference_id", nullable = false)
    private com.sparta.financialadvisorchatbot.entities.Reference reference;

    public JunctionQrId getId() {
        return id;
    }

    public void setId(JunctionQrId id) {
        this.id = id;
    }

    public com.sparta.financialadvisorchatbot.entities.Question getQuestion() {
        return question;
    }

    public void setQuestion(com.sparta.financialadvisorchatbot.entities.Question question) {
        this.question = question;
    }

    public com.sparta.financialadvisorchatbot.entities.Reference getReference() {
        return reference;
    }

    public void setReference(com.sparta.financialadvisorchatbot.entities.Reference reference) {
        this.reference = reference;
    }

}