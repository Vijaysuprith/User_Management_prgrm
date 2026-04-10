package com.ums.dto;

import com.ums.enums.*;
import java.time.LocalDate;

public class UserDTO {

    private String username;
    private String email;
    private String password;
    private String mobile;
    private Gender gender;
    private Role role;
    private LocalDate dob;
    private String address;
    private String dpUrl;

    // GETTERS & SETTERS

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDpUrl() { return dpUrl; }
    public void setDpUrl(String dpUrl) { this.dpUrl = dpUrl; }
}