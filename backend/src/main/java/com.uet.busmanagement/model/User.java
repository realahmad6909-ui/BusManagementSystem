package com.uet.busmanagement.model;

public class User
{
    protected int userId; // protected takay child class access kar sakay
    protected String name;
    protected String email;
    protected String password;
    protected String role;
    protected String phone;
    protected String profilePicPath;

    public User()
    {
    }

    // Getters and Setters
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
}