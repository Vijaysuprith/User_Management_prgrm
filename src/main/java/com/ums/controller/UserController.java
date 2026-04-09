package com.ums.controller;

import com.ums.dto.UserDTO;
import com.ums.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO dto,
                            @RequestParam String password) {
        return userService.registerUser(dto, password);
    }

    // Search
    @GetMapping("/search")
    public UserDTO getUser(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    // View All
    @GetMapping("/all")
    public List<UserDTO> getAll() {
        return userService.getAllUsers();
    }

    // Update
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Integer id,
                          @RequestBody UserDTO dto) {
        return userService.updateUser(id, dto);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "User Deleted";
    }
    
    @GetMapping("/test")
    public String test() {
        return "Working!";
    }
}