package com.todo.todoapp.services.implentations;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.todoapp.dtos.OTPVerificationRequest;
import com.todo.todoapp.dtos.UserRegistrationRequest;
import com.todo.todoapp.models.User;
import com.todo.todoapp.repositories.UserRepository;
import com.todo.todoapp.services.interfaces.UserServiceI;

@Service
public class UserService implements UserServiceI{

    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegistrationRequest userRegistrationRequest) {

        if(!userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword())){
            throw new IllegalArgumentException("Password doesnot match");
        }

        this.checkUserExist(userRegistrationRequest.getUsername(), userRegistrationRequest.getEmailId());

        final String otp = this.generateOTP();
        User user = this.createUser(userRegistrationRequest, otp);
        this.userRepository.save(user);
    }

    private void checkUserExist(String username, String email) {

        if(userRepository.existsByEmailId(email)){
            throw new IllegalArgumentException("User is already exist with email");
        }

        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("User is already exist with username");
        }
    }

    public void verifyOTP(OTPVerificationRequest otpVerificationRequest){

        User user = userRepository.findByEmailId(otpVerificationRequest.getEmail())
                                                .orElseThrow(() -> new IllegalArgumentException("Email is not registered"));

        this.validateOTP(user, otpVerificationRequest.getOtp());

        user.setVerified(true);
        user.setOtp(null);
        user.setOtpGeneratedAt(null);

        userRepository.save(user);

    }

    private User createUser(UserRegistrationRequest userRegistrationRequest, String otp) {

        return User
                .builder()
                .name(userRegistrationRequest.getName())
                .username(userRegistrationRequest.getUsername())
                .emailId(userRegistrationRequest.getEmailId())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .phoneNo(userRegistrationRequest.getPhoneNo())
                .otp(otp)
                .otpGeneratedAt(LocalDateTime.now())
                .isVerified(false)
                .build();
    }

    private void validateOTP(User user, String otp){

        if(!otp.equals(user.getOtp())){
            throw new IllegalArgumentException("Invalid OTP..");
        }

        if(Duration.between(user.getOtpGeneratedAt(), LocalDateTime.now()).toMinutes() > 10){
            throw new IllegalArgumentException("OTP is expired..");
        }
    }

    private String generateOTP() {
        int otp = secureRandom.nextInt(900_000) + 100_000;
        return String.valueOf(otp);
    }


}
