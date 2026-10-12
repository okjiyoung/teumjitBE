package com.example.backend.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "location_data")
public class LocationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 사용자의 위치인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 위도
    @Column(nullable = false)
    private double latitude;

    // 경도
    @Column(nullable = false)
    private double longitude;

    // 위치가 측정된 시간
    @Column(nullable = false)
    private LocalDateTime measuredAt;

    public LocationData() {
    }

    public LocationData(
            User user,
            double latitude,
            double longitude,
            LocalDateTime measuredAt
    ) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.measuredAt = measuredAt;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(LocalDateTime measuredAt) {
        this.measuredAt = measuredAt;
    }
}