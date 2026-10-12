package com.example.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Google Places API 응답 데이터")
public class GooglePlacesResponse {

    @Schema(
        description = "장소 정보 목록"
    )
    private List<Place> places;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Schema(description = "개별 장소 정보")
    public static class Place {

        @Schema(
            description = "장소에 해당하는 유형 목록",
            example = "[\"university\", \"school\", \"library\"]"
        )
        private List<String> types;

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }
}