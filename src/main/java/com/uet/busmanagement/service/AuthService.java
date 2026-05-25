package com.uet.busmanagement.service;

import com.uet.busmanagement.model.User;
import com.uet.busmanagement.repository.UserRepository;

public class AuthService
{

    private UserRepository UserRepository;

    // Constructor mien repository ko initialize kar rahay hain
    public AuthService()
    {
        this.UserRepository = new UserRepository();
    }

    // 1. User Registration Logic
    public String registerUser(User user)
    {
        // Check check lagayein ke email pehle se exist to nahi karti
        if (UserRepository.findByEmail(user.getEmail()) != null)
        {
            return "Email already registered!";
        }

        // Agar bilkul naya user hai to save karwa dein
        boolean isSaved = UserRepository.save(user);
        if (isSaved)
        {
            return "Registration successful!";
        }
        else
        {
            return "Registration failed due to a database error.";
        }
    }

    // 2. User Login Logic
    public User loginUser(String email, String password)
    {
        User user = UserRepository.findByEmail(email);

        // Agar user mil jaye aur password match ho jaye
        if (user != null && user.getPassword().equals(password))
        {
            return user; // Login successful, user ka object wapas bhej diya
        }

        return null; // Login failed
    }
}