package com.example.backend.domain;

import jakarta.persistence.*;

//여기서 닉네임, 이메일, 집 주소 등 유저 정보 관리하게 됨

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private String homeAddress;

    private Double homeLatitude;

    private Double homeLongitude;

    public UserProfile() {
    }

    public UserProfile(
            User user,
            String email,
            String nickname,
            String homeAddress,
            Double homeLatitude,
            Double homeLongitude
    ) {
        this.user = user;
        this.email = email;
        this.nickname = nickname;
        this.homeAddress = homeAddress;
        this.homeLatitude = homeLatitude;
        this.homeLongitude = homeLongitude;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Double getHomeLatitude() {
        return homeLatitude;
    }

    public void setHomeLatitude(Double homeLatitude) {
        this.homeLatitude = homeLatitude;
    }

    public Double getHomeLongitude() {
        return homeLongitude;
    }

    public void setHomeLongitude(Double homeLongitude) {
        this.homeLongitude = homeLongitude;
    }
}