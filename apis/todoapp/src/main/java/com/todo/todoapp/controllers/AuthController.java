package com.todo.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import com.todo.todoapp.dtos.OTPVerificationRequest;
import com.todo.todoapp.dtos.UserRegistrationRequest;
import com.todo.todoapp.services.interfaces.UserServiceI;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserServiceI userServiceI;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        userServiceI.registerUser(userRegistrationRequest);
        return ResponseEntity.ok("User registered successfully. Please check your email for OTP.");
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<String> verifyOTP(@RequestBody OTPVerificationRequest otpVerificationRequest){

        userServiceI.verifyOTP(otpVerificationRequest);
        return ResponseEntity.ok("OTP verified..");
    }
}
