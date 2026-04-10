package com.ums.service;

import com.ums.dto.UserDTO;
import com.ums.entity.User;
import java.util.List;

public interface UserService {

    String register(UserDTO dto);

    List<User> getAll();

    User getByEmail(String email);

    String update(Integer id, UserDTO dto);

    String delete(Integer id);

    String sendOtp(String email);

    String resetPassword(String email, String otp, String newPassword);
}