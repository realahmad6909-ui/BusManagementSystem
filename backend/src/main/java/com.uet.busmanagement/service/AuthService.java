package com.uet.busmanagement.service;

import com.uet.busmanagement.model.User;
import com.uet.busmanagement.repository.UserRepository;
import com.uet.busmanagement.util.LogManager; // <--- 1. Naya Import Add Kiya

public class AuthService
{
    private UserRepository userRepository;

    public AuthService()
    {
        this.userRepository = new UserRepository();
    }


    public boolean isAdminAuthorized(User user) {

        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    public String registerUser(User user)
    {
        if (user.getRole().equals("ADMIN"))
        {
            LogManager.log("SECURITY ALERT: Direct Admin registration attempt blocked for email: " + user.getEmail());
            return "Security Alert: Admin accounts cannot be registered from the public screen!";
        }

        if (userRepository.findByEmail(user.getEmail()) != null)
        {
            return "Email already exists!";
        }

        boolean isSaved = userRepository.save(user);

        if (isSaved)
        {
            LogManager.log("REGISTRATION: New user registered successfully. Email: " + user.getEmail() + " | Role: " + user.getRole());
            return "User registered successfully as " + user.getRole() + "!";
        }
        else
        {
            return "Registration failed.";
        }
    }

    public User handleLogin(String email, String password)
    {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password))
        {
            LogManager.log("LOGIN SUCCESS: User logged in. Email: " + email + " | Role: " + user.getRole());
            return user;
        }
        LogManager.log("LOGIN FAILED: Invalid login attempt for email: " + email);
        return null;
    }
}