package com.UserServices.UserServices.applications.Repositories;


import com.UserServices.UserServices.applications.Models.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, java.util.UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsById(UUID userId);

}

