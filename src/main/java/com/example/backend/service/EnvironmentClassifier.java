package com.example.backend.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnvironmentClassifier {
    // Subway -> School -> Office -> Cafe -> Outdoor -> Others 순으로 판단됨.

    public String classify(List<String> types) {

        if (types == null || types.isEmpty()) {
            return "OTHER";
        }

        // 지하철 
        if (types.contains("subway_station")) {
            return "SUBWAY";
        }

        // 학교 / 대학 / 도서관
        if (types.contains("university")
                || types.contains("school")
                || types.contains("library")) {
            return "SCHOOL";
        }

        // 사무실
        if (types.contains("coworking_space")
                || types.contains("corporate_office")
                || types.contains("business_center")) {
            return "OFFICE";
        }

        // 카페
        if (types.contains("cafe")
                || types.contains("coffee_shop")) {
            return "CAFE";
        }

        // 야외 / 운동
        if (types.contains("park")
                || types.contains("city_park")
                || types.contains("gym")
                || types.contains("fitness_center")) {
            return "OUTDOOR";
        }

        return "OTHER";
    }
}