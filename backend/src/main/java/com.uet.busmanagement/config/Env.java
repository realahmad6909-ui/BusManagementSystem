package com.uet.busmanagement.config;

import java.io.InputStream;
import java.util.Properties;

public class Env {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Env.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.out.println("Warning: application.properties not found, using system env variables.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        // Pehle application.properties check karega, phir system environment variables
        return properties.getProperty(key, System.getenv(key));
    }
}