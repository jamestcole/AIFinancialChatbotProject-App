package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
}
