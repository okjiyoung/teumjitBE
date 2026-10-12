package com.example.backend.service;

import com.example.backend.domain.LocationData;
import com.example.backend.domain.User;
import com.example.backend.dto.LocationDataRequest;
import com.example.backend.repository.LocationDataRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationDataService {

    private final LocationDataRepository locationDataRepository;
    private final UserRepository userRepository;

    public LocationDataService(
            LocationDataRepository locationDataRepository,
            UserRepository userRepository
    ) {
        this.locationDataRepository = locationDataRepository;
        this.userRepository = userRepository;
    }

    public LocationData createLocationData(
            LocationDataRequest request
    ) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        LocationData locationData = new LocationData(
                user,
                request.getLatitude(),
                request.getLongitude(),
                request.getMeasuredAt()
        );

        return locationDataRepository.save(locationData);
    }

    public List<LocationData> getAllLocationData() {
        return locationDataRepository.findAll();
    }

    public List<LocationData> getLocationDataByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        return locationDataRepository.findByUser(user);
    }
}