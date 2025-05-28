package com.todo.todoapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.todoapp.models.User;

public interface UserRepository extends JpaRepository<User, Long> { 

    // Find by email (for OTP verification and login)
    Optional<User> findByEmailId(String emailId);

    // Check if a username already exists
    boolean existsByUsername(String username);

    // Check if an email already exists
    boolean existsByEmailId(String emailId);
}
