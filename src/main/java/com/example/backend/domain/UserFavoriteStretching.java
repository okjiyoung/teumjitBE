package com.example.backend.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "user_favorite_stretchings")
@Schema(description = "사용자가 저장한 스트레칭 루틴")
public class UserFavoriteStretching {

    // 즐겨찾기 루틴 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 즐겨찾기를 등록한 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 루틴 제목 (예: 목 · 어깨 · 다리)
    @Column(nullable = false)
    private String title;

    // 즐겨찾기 등록 시각
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public UserFavoriteStretching() {
    }

    public UserFavoriteStretching(
            User user,
            String title,
            LocalDateTime createdAt
    ) {
        this.user = user;
        this.title = title;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}