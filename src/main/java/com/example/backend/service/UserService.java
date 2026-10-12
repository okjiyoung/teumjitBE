package com.example.backend.service;

import com.example.backend.domain.User;
import com.example.backend.dto.UserRequest;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //유저 작성
    public User createUser(UserRequest request) {

        User user = new User(
                request.getUserId(),
                request.getPassword()
        );

        return userRepository.save(user);
    }

    //유저 조회(전체)
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    //유저 정보 수정
    public User updateUser(
            Long id,
            UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        user.setUserId(request.getUserId());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    //유저 삭제
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("회원을 찾을 수 없습니다.")
                );

        userRepository.delete(user);
    }
}