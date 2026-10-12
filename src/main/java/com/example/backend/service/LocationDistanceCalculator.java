package com.example.backend.service;

import org.springframework.stereotype.Component;

// 이 클래스가 하는 일

// 예를 들어:

// 집

// 위도    37.123456
// 경도    127.123456

// 현재 위치

// 위도    37.123700
// 경도    127.123600

// 두 좌표를 넣으면:

// double distance =
//         locationDistanceCalculator.calculateDistance(
//                 currentLatitude,
//                 currentLongitude,
//                 homeLatitude,
//                 homeLongitude
//         );

// 결과로 미터 단위 거리가 나옵니다.

// 예:

// 35.7

// → 집에서 약 35.7m 떨어져 있다는 뜻입니다.

@Component
public class LocationDistanceCalculator {

    private static final double EARTH_RADIUS_METERS = 6371000;

    public double calculateDistance(
            double latitude1,
            double longitude1,
            double latitude2,
            double longitude2
    ) {

        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);

        double deltaLat =
                Math.toRadians(latitude2 - latitude1);

        double deltaLon =
                Math.toRadians(longitude2 - longitude1);

        double a =
                Math.sin(deltaLat / 2)
                        * Math.sin(deltaLat / 2)
                +
                Math.cos(lat1)
                        * Math.cos(lat2)
                        * Math.sin(deltaLon / 2)
                        * Math.sin(deltaLon / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a)
                );

        return EARTH_RADIUS_METERS * c;
    }
}