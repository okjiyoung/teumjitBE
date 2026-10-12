package com.example.backend.repository;

import com.example.backend.domain.HealthData;
import com.example.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthDataRepository
        extends JpaRepository<HealthData, Long> {

    List<HealthData> findByUser(User user); // findByAll이 아님! 유저 개별 건강 data 가져와야 하기 때문.
}