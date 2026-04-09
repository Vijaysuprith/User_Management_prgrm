package com.ums.service;

import com.ums.dto.UserDTO;
import com.ums.enums.Role;
import com.ums.entity.User;
import com.ums.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Convert Entity → DTO
    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    // Register
    @Override
    public UserDTO registerUser(UserDTO dto, String password) {

        User user = new User(
                null,
                dto.getUsername(),
                dto.getEmail(),
                password,
                dto.getRole() != null ? dto.getRole() : Role.USER,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return toDTO(userRepository.save(user));
    }

    // Get by Email
    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return toDTO(user);
    }

    // Get All
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Update
    @Override
    public UserDTO updateUser(Integer id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setUpdatedAt(LocalDateTime.now());

        return toDTO(userRepository.save(user));
    }

    // Delete
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}