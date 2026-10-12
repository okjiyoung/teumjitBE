package com.example.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "stretchings")
public class Stretching {
    
    //스트레칭 구분 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //스트레칭 이름
    @Column(nullable = false)
    private String name;

    //스트레칭 난이도 구분 : 0 / 1 / 2 == Easy / Middle / Hard
    @Column(nullable = false)
    private int level;

    //스트레칭 부위
    @Column(nullable = false)
    private String area;
    
    //스트레칭 설명 : 동작 실행 방법 설명, 최대 500자 저장됨
    @Column(nullable = false, length = 500)
    private String description;

    //걸리는 시간(/s)
    @Column(nullable = false)
    private int durationSeconds;


    public Stretching() {
    }

    public Stretching(
            String name,
            int level,
            String area,
            String description,
            int durationSeconds
    ) {
        this.name = name;
        this.level = level;
        this.area = area;
        this.description = description;
        this.durationSeconds = durationSeconds;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}