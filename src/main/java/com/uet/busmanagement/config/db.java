package com.uet.busmanagement.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db {
    private static Connection connection = null;

    public static Connection initializeDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");

                // EnvConfig se variables fetch ho rahay hain dynamic tareeqay se
                String url = Env.get("DB_URL");
                String user = Env.get("DB_USER");
                String password = Env.get("DB_PASSWORD");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("MySQL Database Connected successfully via EnvConfig!");
            }
        } catch (Exception e) {
            System.err.println("Database connection initialization failed!");
            e.printStackTrace();
        }
        return connection;
    }
}