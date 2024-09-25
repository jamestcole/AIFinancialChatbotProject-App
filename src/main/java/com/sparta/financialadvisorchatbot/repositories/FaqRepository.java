package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqRepository extends JpaRepository<Faq, Integer> {
    Optional<Faq> findByQuestionIgnoreCase(String question);
}
