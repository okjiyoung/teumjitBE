package com.example.backend.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_data")
public class HealthData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // healthData.getUser() 사용 위해 - healthData별 나누기

    // 어떤 사용자의 건강 데이터인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 데이터 종류 : 움직임, 걸음 수 등
    private String dataType;

    // 측정값
    private double value;

    // 단위
    // 예: count
    private String unit;

    // 측정된 시간
    @Column(nullable = false)
    private LocalDateTime measuredAt; // NULL값 거부

    // 데이터 출처 -> Health Connect
    private String source;

    public HealthData() {
    }

    public HealthData(
            User user, // 어떤 유저에서 얻은 데이터인지
            String dataType, // 데이터 종류 : 움직임, 걸음 수 등
            double value, // 측정값
            String unit, // 단위
            LocalDateTime measuredAt, // 측정된 시간 : 가만히 있는 상태 측정
            String source // 데이터 출처
    ) {
        this.user = user;
        this.dataType = dataType;
        this.value = value;
        this.unit = unit;
        this.measuredAt = measuredAt;
        this.source = source;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(LocalDateTime measuredAt) {
        this.measuredAt = measuredAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}