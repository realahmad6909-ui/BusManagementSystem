package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Student;
import com.uet.busmanagement.model.User;
import com.uet.busmanagement.repository.UserRepository;
import com.uet.busmanagement.util.LogManager; // <--- 1. Naya Import Add Kiya
import org.springframework.stereotype.Service;

@Service
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

    public String registerUser(User user) {

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "Security Alert: Admin accounts cannot be registered from the public screen!";
        }


        if (user.getRole() == null) {
            user.setRole("STUDENT");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email already exists!";
        }

        try {

            boolean isUserSaved = userRepository.save(user);

            if (isUserSaved) {

                if (user instanceof Student) {
                    Student student = (Student) user;

                    User newlyCreatedUser = userRepository.findByEmail(student.getEmail());

                    if (newlyCreatedUser != null) {

                        boolean isStudentSaved = userRepository.saveStudentFields(
                                newlyCreatedUser.getUserId(),
                                student.getName(),
                                student.getEmail(), // 👈 Yeh naya parameter add kiya
                                student.getRegNum(),
                                student.getDepartment(),
                                "PENDING"
                        );
                        if (!isStudentSaved) {
                            return "User created but failed to save student academic details.";
                        }
                    }
                }
                return "Success";
            } else {
                return "Registration failed at user level.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Database Error: " + e.getMessage();
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