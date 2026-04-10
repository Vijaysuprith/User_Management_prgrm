package com.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ums.entity.User;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findByGender(com.ums.enums.Gender gender);

    List<User> findByRole(com.ums.enums.Role role);
}