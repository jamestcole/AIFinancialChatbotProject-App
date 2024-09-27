package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Integer> {
}
