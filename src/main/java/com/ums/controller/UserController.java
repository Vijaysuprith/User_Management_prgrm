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

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO dto) {
        return userService.registerUser(dto);
    }

    @GetMapping("/search")
    public UserDTO getUser(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/all")
    public List<UserDTO> getAll() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Integer id,
                          @RequestBody UserDTO dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "User Deleted Successfully";
    }
}