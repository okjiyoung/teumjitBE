package com.example.backend.repository;

import com.example.backend.domain.Stretching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StretchingRepository extends JpaRepository<Stretching, Long> { //Spring Data JPA 사용
}