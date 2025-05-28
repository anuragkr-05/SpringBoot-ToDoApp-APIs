package com.todo.todoapp.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "User")
@Data
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, name = "Name")
    private String name;

    @NotBlank(message = "Username must be filled")
    @Column(nullable = false , unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true, name = "Email")
    @Email(message = "Email is not valid")
    private String emailId;

    @NotBlank(message = "Password is required")
    @Column(nullable = false, length = 100 )
    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters and include uppercase, lowercase, number, and special character")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone number must be exactly 10 digits")
    @Column(nullable = false, unique = true, length = 10)
    private String phoneNo;

    @Column(name = "is_verified")
    @Builder.Default
    private boolean isVerified = false;

    @Column(name = "otp", length = 6)
    private String otp;

    @Column(name = "otp_generated_at")
    private LocalDateTime otpGeneratedAt;

}
