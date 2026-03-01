package com.yjh107.minilog.service;

import com.yjh107.minilog.dto.UserRequestDto;
import com.yjh107.minilog.dto.UserResponseDto;
import com.yjh107.minilog.entity.Role;
import com.yjh107.minilog.entity.User;
import com.yjh107.minilog.exception.NotAuthorizedException;
import com.yjh107.minilog.exception.UserNotFoundException;
import com.yjh107.minilog.repository.UserRepository;
import com.yjh107.minilog.security.MinilogUserDetails;
import com.yjh107.minilog.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
    사용자 관련 비즈니스 로직을 관리하는 서비스 클래스
    사용자의 생성, 조회, 업데이트, 삭제 기능을 포함
    데이터베이스와의 상호작용을 위해 UserRepository를 사용
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream()
                .map(EntityDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserResponseDto> getUserById(Long userId) {
        return userRepository.findById(userId).map(EntityDtoMapper::toDto);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        // 사용자 생성 시 ROLE_USER 권한을 부여합니다.
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_AUTHOR);

        // 사용자 이름이 admin인 경우 ROLE_ADMIN 권한을 추가합니다.
        // NOTE: 실제로는 이렇게 하지 않지만 예제의 단순화를 위해 하드코딩
        if (userRequestDto.getUsername().equals("admin")) {
            roles.add(Role.ROLE_ADMIN);
        }

        User savedUser =
                userRepository.save(
                        User.builder()
                                .username(userRequestDto.getUsername())
                                .password(userRequestDto.getPassword())
                                .roles(roles)
                                .build());
        return EntityDtoMapper.toDto(savedUser);
    }

    public UserResponseDto updateUser(MinilogUserDetails userDetails, Long userId, UserRequestDto userRequestDto) {
        if (!userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Role.ROLE_ADMIN.name()))
                && !userDetails.getId().equals(userId)) {
            throw new NotAuthorizedException("권한이 없습니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());

        var updatedUser = userRepository.save(user);
        return EntityDtoMapper.toDto(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        String.format("해당 아이디(%d)를 가진 사용자를 찾을 수 없습니다.", userId)));

        userRepository.deleteById(userId);
    }

    public UserResponseDto getUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(EntityDtoMapper::toDto)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                String.format("해당 이름(%s)을 가진 사용자를 찾을 수 없습니다.", username)
                        )
                );
    }
}
