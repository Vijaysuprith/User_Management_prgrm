package com.ums.dto;

import com.ums.enums.Role;

public class UserDTO {

    private Integer userId;
    private String username;
    private String email;
    private Role role;

    // No password 🔐

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}