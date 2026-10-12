package com.example.backend.service;

import com.example.backend.domain.Stretching;
import com.example.backend.dto.StretchingRequest;
import com.example.backend.repository.StretchingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StretchingService {

    private final StretchingRepository stretchingRepository;

    public StretchingService(StretchingRepository stretchingRepository) {
        this.stretchingRepository = stretchingRepository;
    }

    //스트레칭 등록
    public Stretching createStretching(StretchingRequest request) {

        Stretching stretching = new Stretching(
                request.getName(),
                request.getLevel(),
                request.getArea(),
                request.getDescription(),
                request.getDurationSeconds()
        );

        return stretchingRepository.save(stretching);
    }

    //스트레칭 전체 조회
    public List<Stretching> getAllStretchings() {

        return stretchingRepository.findAll();
    }

    //스트레칭 ID로 조회
    public Stretching getStretching(Long id) {

        return stretchingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("스트레칭을 찾을 수 없습니다.")
                );
    }

    //스트레칭 수정
    public Stretching updateStretching(
            Long id,
            StretchingRequest request) {

        Stretching stretching = stretchingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("스트레칭을 찾을 수 없습니다.")
                );

        stretching.setName(request.getName());
        stretching.setLevel(request.getLevel());
        stretching.setArea(request.getArea());
        stretching.setDescription(request.getDescription());
        stretching.setDurationSeconds(request.getDurationSeconds());

        return stretchingRepository.save(stretching);
    }

    //스트레칭 삭제
    public void deleteStretching(Long id) {

        Stretching stretching = stretchingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("스트레칭을 찾을 수 없습니다.")
                );

        stretchingRepository.delete(stretching);
    }
}