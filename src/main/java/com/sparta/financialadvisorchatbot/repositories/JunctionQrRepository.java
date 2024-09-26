package com.sparta.financialadvisorchatbot.repositories;

import com.sparta.financialadvisorchatbot.entities.JunctionQr;
import com.sparta.financialadvisorchatbot.entities.JunctionQrId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JunctionQrRepository extends JpaRepository<JunctionQr, JunctionQrId> {
}
