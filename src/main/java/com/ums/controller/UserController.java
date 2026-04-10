package com.ums.controller;

import com.ums.dto.UserDTO;
import com.ums.entity.User;
import com.ums.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin   // 🔥 IMPORTANT for frontend
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public String register(@RequestBody UserDTO dto) {
        return service.register(dto);
    }

    @GetMapping("/all")
    public List<User> all() {
        return service.getAll();
    }

    @GetMapping("/email")
    public User get(@RequestParam String email) {
        return service.getByEmail(email);
    }

    // 🔥 FORGOT PASSWORD
    @PostMapping("/forgot")
    public String forgot(@RequestParam String email) {
        return service.sendOtp(email);
    }

    // 🔥 RESET PASSWORD
    @PostMapping("/reset")
    public String reset(@RequestParam String email,
                        @RequestParam String otp,
                        @RequestParam String newPassword) {
        return service.resetPassword(email, otp, newPassword);
    }
}