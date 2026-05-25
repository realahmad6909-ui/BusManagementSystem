package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.User;
import com.uet.busmanagement.service.AuthService;

public class AuthController
{

    private AuthService authService;

    public AuthController()
    {
        this.authService = new AuthService();
    }

    // 1. View (Screen) se registration ka data receive karne ke liye
    public String handleRegistration(int userId, String name, String email, String password, String role, String phone, String profilePicPath)
    {
        // Encapsulation ke mutabiq pehle User ka object banaya
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setPhone(phone);
        newUser.setProfilePicPath(profilePicPath);

        // Service ko data bhej kar result wapas return kar diya
        return authService.registerUser(newUser);
    }

    // 2. View se login credentials receive karne ke liye
    public User handleLogin(String email, String password)
    {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty())
        {
            return null;
        }

        return authService.loginUser(email, password);
    }
}