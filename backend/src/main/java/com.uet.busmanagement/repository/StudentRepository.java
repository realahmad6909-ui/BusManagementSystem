package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    public void registerstudent(Student student) {
        String userSql = "INSERT INTO users (name, email, password, role, phone) VALUES (?, ?, ?, 'STUDENT', ?)";
        String studentSql = "INSERT INTO students (name, department, regnum, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase()) {
            conn.setAutoCommit(false); // Transaction shuru

            // 1. Insert into 'users'
            PreparedStatement ps1 = conn.prepareStatement(userSql);
            ps1.setString(1, student.getName());
            ps1.setString(2, student.getEmail());
            ps1.setString(3, student.getPassword());
            ps1.setString(4, student.getPhone());
            ps1.executeUpdate();

            // 2. Insert into 'students' (email yahan bridge ka kaam kar raha hai)
            PreparedStatement ps2 = conn.prepareStatement(studentSql);
            ps2.setString(1, student.getName());
            ps2.setString(2, student.getDepartment());
            ps2.setString(3, student.getRegNum());
            ps2.setString(4, student.getEmail());
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Registration successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean updateStatus(int studentId, String status) {
        String query = "UPDATE students SET status = ? WHERE student_id = ?";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, studentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Student findStudent(int studentId) {
        String query = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student findStudentbyregnumber(String regNum) {
        String query = "SELECT * FROM students WHERE regnum = ?";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, regNum);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }




    private Student mapResultSetToStudent(ResultSet rs) throws Exception {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setRegNum(rs.getString("regnum"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setDepartment(rs.getString("department"));
        s.setStatus(rs.getString("status"));
        return s;
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
    // StudentRepository.java
    public boolean updateStudentInfo(Student student) {
        String query = "UPDATE students SET name = ?, department = ? WHERE regnum = ?";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getDepartment());
            stmt.setString(3, student.getRegNum());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
    public Student findByEmailAndPassword(String email, String password) {
        String query = "SELECT s.* FROM students s " +
                "JOIN users u ON s.student_id = u.user_id " + // Assuming student_id maps to user_id
                "WHERE u.email = ? AND u.password = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student s = new Student();
                s.setRegNum(rs.getString("regnum"));
                s.setName(rs.getString("name"));
                s.setDepartment(rs.getString("department"));
                return s;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    public void deleteStudent(String email) {
        // Transaction start: dono tables se data delete karein
        String deleteStudentSql = "DELETE FROM students WHERE email = ?";
        String deleteUserSql = "DELETE FROM users WHERE email = ?";

        try (Connection conn = db.initializeDatabase()) {
            conn.setAutoCommit(false); // Transaction shuru

            // 1. Pehle students table se delete karein
            try (PreparedStatement ps1 = conn.prepareStatement(deleteStudentSql)) {
                ps1.setString(1, email);
                ps1.executeUpdate();
            }

            // 2. Phir users table se delete karein
            try (PreparedStatement ps2 = conn.prepareStatement(deleteUserSql)) {
                ps2.setString(1, email);
                ps2.executeUpdate();
            }

            conn.commit(); // Sab sahi raha toh save kar dein
            System.out.println("Student deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}