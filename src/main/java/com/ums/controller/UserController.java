package com.ums.controller;

import com.ums.dto.*;
import com.ums.entity.User;
import com.ums.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    // ✅ REGISTER
    @PostMapping("/register")
    public String register(@RequestBody UserDTO dto) {
        return service.register(dto);
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

    // ✅ GET ALL USERS
    @GetMapping("/all")
    public List<User> allUsers() {
        return service.getAll();
    }

    // ✅ GET BY EMAIL
    @GetMapping("/email")
    public User getByEmail(@RequestParam String email) {
        return service.getByEmail(email);
    }

    // 🔥 FORGOT PASSWORD (SEND OTP)
    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam String email) {
        return service.sendOtp(email);
    }

    // 🔐 RESET PASSWORD
    @PostMapping("/reset")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String otp,
                                @RequestParam String newPassword) {
        return service.resetPassword(email, otp, newPassword);
    }

    // ✅ UPDATE USER
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Integer id,
                            @RequestBody UserDTO dto) {
        return service.update(id, dto);
    }

    // ✅ DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        return service.delete(id);
    }
}