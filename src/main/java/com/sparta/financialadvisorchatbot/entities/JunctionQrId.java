package com.sparta.financialadvisorchatbot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class JunctionQrId implements java.io.Serializable {
    private static final long serialVersionUID = -4065905137351310113L;
    @NotNull
    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @NotNull
    @Column(name = "reference_id", nullable = false)
    private Integer referenceId;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JunctionQrId entity = (JunctionQrId) o;
        return Objects.equals(this.questionId, entity.questionId) &&
                Objects.equals(this.referenceId, entity.referenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, referenceId);
    }

}