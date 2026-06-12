package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {

    public boolean saveStudentFields(int userId, String name, String email, String regNum, String department, String status) {

        String query = "INSERT INTO students (student_id, name, email, regnum, department, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, regNum);
            stmt.setString(5, department);
            stmt.setString(6, status);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println(" ERROR IN STUDENTS TABLE INSERTION:");
            e.printStackTrace();
            return false;
        }
    }
    public boolean save(User user) {
        String query = "INSERT INTO users (name, email, password, role, phone, profile_pic_path) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getName() != null ? user.getName() : "Unknown");
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getPhone() != null ? user.getPhone() : "");
            stmt.setString(6, user.getProfilePicPath() != null ? user.getProfilePicPath() : "");

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println("ERROR IN USER REPOSITORY SAVE:");
            e.printStackTrace();
            return false;
        }
    }

    public User findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));
                user.setProfilePicPath(rs.getString("profile_pic_path"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}