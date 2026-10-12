package com.example.backend.repository;

import com.example.backend.domain.User;
import com.example.backend.domain.UserFavoriteStretching;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteStretchingRepository
        extends JpaRepository<UserFavoriteStretching, Long> {

    // 특정 사용자가 저장한 모든 루틴 조회
    List<UserFavoriteStretching> findByUserOrderByCreatedAtDesc(
            User user
    );

    // 특정 사용자의 루틴인지 확인
    boolean existsByIdAndUser(
            Long id,
            User user
    );
}