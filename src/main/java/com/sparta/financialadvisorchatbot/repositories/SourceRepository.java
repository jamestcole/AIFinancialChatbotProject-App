package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source, Integer> {
}
