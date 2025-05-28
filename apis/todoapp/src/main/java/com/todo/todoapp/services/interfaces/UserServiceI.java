package com.todo.todoapp.services.interfaces;

import com.todo.todoapp.dtos.UserRegistrationRequest;

public interface UserServiceI {
    void registerUser(UserRegistrationRequest registrationRequest);
}
