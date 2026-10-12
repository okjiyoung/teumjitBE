package com.example.backend.repository;

import com.example.backend.domain.LocationData;
import com.example.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDataRepository
        extends JpaRepository<LocationData, Long> {

    List<LocationData> findByUser(User user);
}