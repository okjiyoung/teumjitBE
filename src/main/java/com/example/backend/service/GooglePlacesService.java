package com.example.backend.service;

import com.example.backend.dto.GooglePlacesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GooglePlacesService {

    private static final String PLACES_URL =
            "https://places.googleapis.com/v1/places:searchNearby";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${google.maps.api-key}")
    private String apiKey;

    public GooglePlacesService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public GooglePlacesResponse searchNearby(
            double latitude,
            double longitude
    ) {

        String requestBody = """
                {
                  "includedTypes": [
                    "university",
                    "school",
                    "library",
                    "cafe",
                    "coffee_shop",
                    "coworking_space",
                    "corporate_office",
                    "business_center",
                    "subway_station",
                    "park",
                    "city_park",
                    "gym",
                    "fitness_center"
                  ],
                  "maxResultCount": 10,
                  "rankPreference": "DISTANCE",
                  "locationRestriction": {
                    "circle": {
                      "center": {
                        "latitude": %f,
                        "longitude": %f
                      },
                      "radius": 500.0
                    }
                  }
                }
                """.formatted(latitude, longitude);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set(
                "X-Goog-Api-Key",
                apiKey
        );

        headers.set(
                "X-Goog-FieldMask",
                "places.types"
        );

        HttpEntity<String> entity =
                new HttpEntity<>(
                        requestBody,
                        headers
                );

        ResponseEntity<String> response =
                restTemplate.exchange(
                        PLACES_URL,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        try {

            return objectMapper.readValue(
                    response.getBody(),
                    GooglePlacesResponse.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Google Places 응답을 처리할 수 없습니다.",
                    e
            );
        }
    }
}