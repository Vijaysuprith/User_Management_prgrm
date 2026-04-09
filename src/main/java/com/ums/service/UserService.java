package com.ums.service;

import com.ums.dto.UserDTO;
import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO dto, String password);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Integer id, UserDTO dto);

    void deleteUser(Integer id);
}