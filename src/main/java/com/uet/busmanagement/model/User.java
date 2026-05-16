package com.uet.busmanagement.model;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role; // ADMIN, STUDENT, DRIVER
    private String phone;
    private String profilePicPath; // FOR File handling

    // Parameterized Constructor (
    public User(int userId, String name, String email, String password, String role, String phone, String profilePicPath)
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.profilePicPath = profilePicPath;
    }

    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }
    public void setRole(String role)
    {
        this.role = role;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getProfilePicPath()
    {
        return profilePicPath;
    }
    public void setProfilePicPath(String profilePicPath)
    {
        this.profilePicPath = profilePicPath;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + userId +
                ", Name='" + name + '\'' +
                ", Email='" + email + '\'' +
                ", Role='" + role + '\'' +
                ", Phone='" + phone + '\'' +
                '}';
    }
}