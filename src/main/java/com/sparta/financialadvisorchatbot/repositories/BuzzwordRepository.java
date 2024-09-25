package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.Buzzword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuzzwordRepository extends JpaRepository<Buzzword, Integer> {
}
