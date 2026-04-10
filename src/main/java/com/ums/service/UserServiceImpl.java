package com.ums.service;

import com.ums.dto.UserDTO;
import com.ums.entity.User;
import com.ums.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private EmailService emailService;

    // ✅ REGISTER
    @Override
    public String register(UserDTO dto) {

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setMobile(dto.getMobile());
        user.setGender(dto.getGender());
        user.setRole(dto.getRole());
        user.setDob(dto.getDob());
        user.setAddress(dto.getAddress());
        user.setDpUrl(dto.getDpUrl());

        user.setCreatedAt(LocalDateTime.now());

        repo.save(user);

        return "User Registered Successfully";
    }

    // ✅ GET ALL
    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    // ✅ GET BY EMAIL (SAFE)
    @Override
    public User getByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    // ✅ UPDATE
    @Override
    public String update(Integer id, UserDTO dto) {

        User user = repo.findById(id).orElse(null);

        if (user == null) return "User not found";

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

        return "Updated Successfully";
    }

    // ✅ DELETE
    @Override
    public String delete(Integer id) {
        repo.deleteById(id);
        return "Deleted Successfully";
    }

    // 🔥🔥🔥 MAIN FIX — SEND OTP
    @Override
    public String sendOtp(String email) {

        System.out.println("EMAIL RECEIVED: " + email);

        User user = repo.findByEmail(email).orElse(null);

        if (user == null) {
            return "❌ User not found with this email";
        }

        System.out.println("USER FOUND: " + user.getEmail());

        // Generate OTP (6 digits)
        String otp = String.format("%06d", new Random().nextInt(999999));

        System.out.println("OTP GENERATED: " + otp);

        // Save OTP in DB
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        repo.save(user);   // 🔥 IMPORTANT: save BEFORE sending mail

        // Send email
        try {
            emailService.sendOtp(email, otp);
        } catch (Exception e) {
            e.printStackTrace();
            return "⚠️ OTP generated but email sending failed";
        }

        return "✅ OTP Sent Successfully";
    }

    // 🔥 RESET PASSWORD
    @Override
    public String resetPassword(String email, String otp, String newPassword) {

        User user = repo.findByEmail(email).orElse(null);

        if (user == null) return "User not found";

        if (user.getOtp() == null) return "No OTP generated";

        if (!user.getOtp().equals(otp)) return "Invalid OTP";

        if (user.getOtpExpiry().isBefore(LocalDateTime.now()))
            return "OTP Expired";

        user.setPassword(newPassword);
        user.setOtp(null); // clear OTP
        user.setOtpExpiry(null);

        repo.save(user);

        return "✅ Password Updated Successfully";
    }
}