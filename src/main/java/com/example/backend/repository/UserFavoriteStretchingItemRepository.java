package com.example.backend.repository;

import com.example.backend.domain.UserFavoriteStretchingItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteStretchingItemRepository
        extends JpaRepository<UserFavoriteStretchingItem, Long> {

    // 특정 즐겨찾기 루틴의 항목을 실행 순서대로 조회
    List<UserFavoriteStretchingItem> findByFavorite_IdOrderBySequenceOrderAsc(
            Long favoriteId
    );

    // 특정 즐겨찾기 루틴에 포함된 모든 항목 조회
    List<UserFavoriteStretchingItem> findByFavorite_Id(
            Long favoriteId
    );

    // 특정 즐겨찾기 루틴의 항목 전체 삭제
    void deleteByFavorite_Id(Long favoriteId);
}