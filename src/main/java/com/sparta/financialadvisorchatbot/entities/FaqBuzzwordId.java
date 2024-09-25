package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class FaqBuzzwordId implements java.io.Serializable {
    private static final long serialVersionUID = -2748154317970415114L;
    @NotNull
    @Column(name = "FAQ_ID", nullable = false)
    private Integer faqId;

    @NotNull
    @Column(name = "Buzzword_ID", nullable = false)
    private Integer buzzwordId;

    public Integer getFaqId() {
        return faqId;
    }

    public void setFaqId(Integer faqId) {
        this.faqId = faqId;
    }

    public Integer getBuzzwordId() {
        return buzzwordId;
    }

    public void setBuzzwordId(Integer buzzwordId) {
        this.buzzwordId = buzzwordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FaqBuzzwordId entity = (FaqBuzzwordId) o;
        return Objects.equals(this.buzzwordId, entity.buzzwordId) &&
                Objects.equals(this.faqId, entity.faqId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buzzwordId, faqId);
    }

}