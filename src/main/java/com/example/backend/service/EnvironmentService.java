package com.example.backend.service;

import com.example.backend.domain.UserProfile;
import com.example.backend.dto.EnvironmentResponse;
import com.example.backend.dto.GooglePlacesResponse;
import com.example.backend.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentService {

    private static final double HOME_DISTANCE_THRESHOLD = 50.0;

    private final UserProfileRepository userProfileRepository;
    private final LocationDistanceCalculator locationDistanceCalculator;
    private final GooglePlacesService googlePlacesService;
    private final EnvironmentClassifier environmentClassifier;

    public EnvironmentService(
            UserProfileRepository userProfileRepository,
            LocationDistanceCalculator locationDistanceCalculator,
            GooglePlacesService googlePlacesService,
            EnvironmentClassifier environmentClassifier
    ) {
        this.userProfileRepository =
                userProfileRepository;

        this.locationDistanceCalculator =
                locationDistanceCalculator;

        this.googlePlacesService =
                googlePlacesService;

        this.environmentClassifier =
                environmentClassifier;
    }

    public EnvironmentResponse determineEnvironment(
            Long userId,
            double currentLatitude,
            double currentLongitude
    ) {

        UserProfile profile =
                userProfileRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "사용자 프로필을 찾을 수 없습니다."
                                )
                        );

        Double homeLatitude =
                profile.getHomeLatitude();

        Double homeLongitude =
                profile.getHomeLongitude();

        // 집 좌표가 없는 경우
        if (homeLatitude == null
                || homeLongitude == null) {

            return new EnvironmentResponse(
                    "OTHER",
                    -1
            );
        }

        // 현재 위치와 집의 거리 계산
        double distanceFromHome =
                locationDistanceCalculator.calculateDistance(
                        currentLatitude,
                        currentLongitude,
                        homeLatitude,
                        homeLongitude
                );

        // 50m 이내이면 HOME
        if (distanceFromHome <= HOME_DISTANCE_THRESHOLD) {

            return new EnvironmentResponse(
                    "HOME",
                    distanceFromHome
            );
        }

        // HOME이 아니면 Google Places 검색
        GooglePlacesResponse placesResponse =
                googlePlacesService.searchNearby(
                        currentLatitude,
                        currentLongitude
                );

        if (placesResponse.getPlaces() == null
                || placesResponse.getPlaces().isEmpty()) {

            return new EnvironmentResponse(
                    "OTHER",
                    distanceFromHome
            );
        }

        // 가장 가까운 장소부터 확인
        for (GooglePlacesResponse.Place place
                : placesResponse.getPlaces()) {

            List<String> types =
                    place.getTypes();

            String environment =
                    environmentClassifier.classify(
                            types
                    );

            if (!environment.equals("OTHER")) {

                return new EnvironmentResponse(
                        environment,
                        distanceFromHome
                );
            }
        }

        return new EnvironmentResponse(
                "OTHER",
                distanceFromHome
        );
    }
}