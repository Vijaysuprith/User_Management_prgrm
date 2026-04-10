package com.ums.service;

import com.ums.dto.*;
import com.ums.entity.User;
import com.ums.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // ✅ REGISTER
    @Override
    public String register(UserDTO dto) {

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        // 🔐 Encrypt password
        user.setPassword(encoder.encode(dto.getPassword()));

        user.setMobile(dto.getMobile());
        user.setGender(dto.getGender());
        user.setRole(dto.getRole());
        user.setDob(dto.getDob());
        user.setAddress(dto.getAddress());
        user.setDpUrl(dto.getDpUrl());

        user.setCreatedAt(LocalDateTime.now());

        repo.save(user);

        return "✅ User Registered Successfully";
    }

    // ✅ LOGIN
    @Override
    public String login(LoginDTO dto) {

        User user = repo.findByEmail(dto.getEmail()).orElse(null);

        if (user == null) {
            return "❌ User not found";
        }

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            return "❌ Invalid Password";
        }

        return "✅ Login Successful";
    }

    // ✅ GET ALL USERS
    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    // ✅ GET BY EMAIL
    @Override
    public User getByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    // 🔥 SEND OTP (FORGOT PASSWORD)
    @Override
    public String sendOtp(String email) {

        System.out.println("EMAIL RECEIVED: " + email);

        User user = repo.findByEmail(email).orElse(null);

        if (user == null) {
            return "❌ User not found with this email";
        }

        String otp = String.format("%06d", new Random().nextInt(999999));

        System.out.println("OTP GENERATED: " + otp);

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        repo.save(user); // ✅ save before sending mail

        try {
            emailService.sendOtp(email, otp);
        } catch (Exception e) {
            e.printStackTrace();
            return "⚠️ OTP generated but email sending failed";
        }

        return "✅ OTP Sent Successfully";
    }

    // 🔐 RESET PASSWORD
    @Override
    public String resetPassword(String email, String otp, String newPassword) {

        User user = repo.findByEmail(email).orElse(null);

        if (user == null) return "❌ User not found";

        if (user.getOtp() == null) return "❌ No OTP generated";

        if (!user.getOtp().equals(otp)) return "❌ Invalid OTP";

        if (user.getOtpExpiry().isBefore(LocalDateTime.now()))
            return "❌ OTP Expired";

        // 🔐 Encrypt new password
        user.setPassword(encoder.encode(newPassword));

        user.setOtp(null);
        user.setOtpExpiry(null);

        repo.save(user);

        return "✅ Password Updated Successfully";
    }

    // ✅ UPDATE USER
    @Override
    public String update(Integer id, UserDTO dto) {

        User user = repo.findById(id).orElse(null);

        if (user == null) return "❌ User not found";

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setGender(dto.getGender());
        user.setRole(dto.getRole());
        user.setDob(dto.getDob());
        user.setAddress(dto.getAddress());
        user.setDpUrl(dto.getDpUrl());

        user.setUpdatedAt(LocalDateTime.now());

        repo.save(user);

        return "✅ Updated Successfully";
    }

    // ✅ DELETE USER
    @Override
    public String delete(Integer id) {
        repo.deleteById(id);
        return "✅ Deleted Successfully";
    }
}