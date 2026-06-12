package com.uet.busmanagement.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class db
{

    // Yahan agar aap ke database ka naam kuch aur hai toh 'uet_bus_db' ki jagah woh likh dein
    private static final String URL = "jdbc:mysql://localhost:3306/uet_bus_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "iamahmad";
    public static Connection initializeDatabase()
    {
        Connection conn = null;
        try
        {
            // Naya aur updated driver class path automatically load ho jata hai,
            // lekin safe side ke liye hum ne isay register kar diya hai
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Hardcoded credentials pass kar diye taake 'URL cannot be null' ka error kabhi na aaye
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (Exception e)
        {
            System.out.println("Database connection initialization failed!");
            e.printStackTrace();
        }
        return conn;
    }
}