package com.example.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(
    name = "user_body_preferences",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "user_id",
                "preference_type",
                "body_area"
            }
        )
    }
)
public class UserBodyPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String preferenceType;

    @Column(nullable = false)
    private String bodyArea;

    public UserBodyPreference() {
    }

    public UserBodyPreference(
            User user,
            String preferenceType,
            String bodyArea
    ) {
        this.user = user;
        this.preferenceType = preferenceType;
        this.bodyArea = bodyArea;
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

    public String getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(String preferenceType) {
        this.preferenceType = preferenceType;
    }

    public String getBodyArea() {
        return bodyArea;
    }

    public void setBodyArea(String bodyArea) {
        this.bodyArea = bodyArea;
    }
}