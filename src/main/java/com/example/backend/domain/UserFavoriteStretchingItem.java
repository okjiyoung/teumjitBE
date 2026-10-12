package com.example.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_favorite_stretching_items")
public class UserFavoriteStretchingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 즐겨찾기 루틴에 포함된 항목인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_id", nullable = false)
    private UserFavoriteStretching favorite;

    // 루틴에 포함된 스트레칭
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stretching_id", nullable = false)
    private Stretching stretching;

    // 루틴에서 실행할 순서 (1부터 시작)
    @Column(name = "sequence_order", nullable = false)
    private int sequenceOrder;

    public UserFavoriteStretchingItem() {
    }

    public UserFavoriteStretchingItem(
            UserFavoriteStretching favorite,
            Stretching stretching,
            int sequenceOrder
    ) {
        this.favorite = favorite;
        this.stretching = stretching;
        this.sequenceOrder = sequenceOrder;
    }

    public Long getId() {
        return id;
    }

    public UserFavoriteStretching getFavorite() {
        return favorite;
    }

    public void setFavorite(UserFavoriteStretching favorite) {
        this.favorite = favorite;
    }

    public Stretching getStretching() {
        return stretching;
    }

    public void setStretching(Stretching stretching) {
        this.stretching = stretching;
    }

    public int getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(int sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }
}