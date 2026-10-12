package com.example.backend.service;

import com.example.backend.domain.Stretching;
import com.example.backend.domain.User;
import com.example.backend.domain.UserFavoriteStretching;
import com.example.backend.domain.UserFavoriteStretchingItem;
import com.example.backend.dto.UserFavoriteStretchingRequest;
import com.example.backend.dto.UserFavoriteStretchingResponse;
import com.example.backend.dto.UserFavoriteStretchingResponse.StretchingItemResponse;
import com.example.backend.repository.StretchingRepository;
import com.example.backend.repository.UserFavoriteStretchingItemRepository;
import com.example.backend.repository.UserFavoriteStretchingRepository;
import com.example.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFavoriteStretchingService {

    private final UserFavoriteStretchingRepository favoriteRepository;
    private final UserFavoriteStretchingItemRepository favoriteItemRepository;
    private final UserRepository userRepository;
    private final StretchingRepository stretchingRepository;

    public UserFavoriteStretchingService(
            UserFavoriteStretchingRepository favoriteRepository,
            UserFavoriteStretchingItemRepository favoriteItemRepository,
            UserRepository userRepository,
            StretchingRepository stretchingRepository
    ) {
        this.favoriteRepository = favoriteRepository;
        this.favoriteItemRepository = favoriteItemRepository;
        this.userRepository = userRepository;
        this.stretchingRepository = stretchingRepository;
    }

    // 즐겨찾기 루틴 저장
    @Transactional
    public UserFavoriteStretchingResponse createFavorite(
            UserFavoriteStretchingRequest request
    ) {

        if (request.getStretchingIds() == null
                || request.getStretchingIds().isEmpty()) {
            throw new RuntimeException(
                    "즐겨찾기에 등록할 스트레칭이 없습니다."
            );
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "회원을 찾을 수 없습니다."
                        )
                );

        // 요청에 전달된 순서를 유지하며 스트레칭 조회
        List<Stretching> stretchings = new ArrayList<>();

        for (Long stretchingId : request.getStretchingIds()) {

            if (stretchingId == null) {
                throw new RuntimeException(
                        "스트레칭 ID가 비어 있습니다."
                );
            }

            Stretching stretching =
                    stretchingRepository.findById(stretchingId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "스트레칭을 찾을 수 없습니다. ID: "
                                                    + stretchingId
                                    )
                            );

            stretchings.add(stretching);
        }

        // 스트레칭 부위를 조합해 루틴 제목 생성
        String title = createRoutineTitle(stretchings);

        // 루틴 기본 정보 저장
        UserFavoriteStretching favorite =
                new UserFavoriteStretching(
                        user,
                        title,
                        LocalDateTime.now()
                );

        favorite = favoriteRepository.save(favorite);

        // 루틴에 포함된 스트레칭과 실행 순서 저장
        for (int i = 0; i < stretchings.size(); i++) {

            UserFavoriteStretchingItem item =
                    new UserFavoriteStretchingItem(
                            favorite,
                            stretchings.get(i),
                            i + 1
                    );

            favoriteItemRepository.save(item);
        }

        // 저장된 루틴을 응답 DTO로 변환
        return toResponse(favorite);
    }

    // 스트레칭 부위를 조합해 제목 생성
    private String createRoutineTitle(
            List<Stretching> stretchings
    ) {

        Set<String> areas = stretchings.stream()
                .map(Stretching::getArea)
                .filter(area -> area != null && !area.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (areas.isEmpty()) {
            return "저장한 스트레칭 루틴";
        }

        return String.join(" · ", areas);
    }

    // 특정 사용자의 즐겨찾기 루틴 목록 조회
    @Transactional(readOnly = true)
    public List<UserFavoriteStretchingResponse> getFavorites(
            Long userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "회원을 찾을 수 없습니다."
                        )
                );

        return favoriteRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 즐겨찾기 루틴을 응답 DTO로 변환
    private UserFavoriteStretchingResponse toResponse(
            UserFavoriteStretching favorite
    ) {

        List<UserFavoriteStretchingItem> items =
                favoriteItemRepository
                        .findByFavorite_IdOrderBySequenceOrderAsc(
                                favorite.getId()
                        );

        List<StretchingItemResponse> stretchingResponses =
                items.stream()
                        .map(item -> {
                            Stretching stretching = item.getStretching();

                            return new StretchingItemResponse(
                                    stretching.getId(),
                                    stretching.getName(),
                                    stretching.getArea(),
                                    stretching.getDescription(),
                                    stretching.getLevel(),
                                    stretching.getDurationSeconds(),
                                    item.getSequenceOrder()
                            );
                        })
                        .collect(Collectors.toList());

        return new UserFavoriteStretchingResponse(
                favorite.getId(),
                favorite.getUser().getId(),
                favorite.getTitle(),
                favorite.getCreatedAt(),
                stretchingResponses
        );
    }

    // 특정 즐겨찾기 루틴 삭제
    @Transactional
    public void deleteFavorite(Long id) {

        UserFavoriteStretching favorite =
                favoriteRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "해당 즐겨찾기 루틴을 찾을 수 없습니다."
                                )
                        );

        // 루틴에 연결된 항목부터 삭제
        favoriteItemRepository.deleteByFavorite_Id(
                favorite.getId()
        );

        // 루틴 기본 정보 삭제
        favoriteRepository.delete(favorite);
    }
}