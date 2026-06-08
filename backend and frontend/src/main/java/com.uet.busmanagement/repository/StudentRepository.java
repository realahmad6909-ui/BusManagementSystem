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
        // FIX: JOIN lagaya taaki 'users' table se phone number bhi load ho sake
        String query = "SELECT s.*, u.phone FROM students s " +
                "JOIN users u ON s.email = u.email " +
                "WHERE s.regnum = ?";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, regNum);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Student s = mapResultSetToStudent(rs);
                // Yahan users table se phone nikal kar student object mein set kar rahe hain
                s.setPhone(rs.getString("phone"));
                return s;
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
        // FIX: Using email field as bridge constraint instead of wrong ids auto increment match
        String query = "SELECT s.* FROM students s " +
                "JOIN users u ON s.email = u.email " +
                "WHERE u.email = ? AND u.password = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                s.setRegNum(rs.getString("regnum"));
                s.setName(rs.getString("name"));
                s.setDepartment(rs.getString("department"));
                s.setEmail(rs.getString("email"));
                return s;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    public String getLatestNoticeForRoute(String studentRoute) {
        // Ab ye query bina kisi route check ke hamesha sabse naya message uthayegi
        String query = "SELECT message FROM student_notices ORDER BY id DESC LIMIT 1";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();



            if (rs.next()) {
                return rs.getString("message");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteStudent(String email) {

        String findIdSql = "SELECT user_id FROM users WHERE email = ?";
        String deleteStudentSql = "DELETE FROM students WHERE student_id = ?";
        String deleteUserSql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = db.initializeDatabase()) {
            int userId = -1;

            try (PreparedStatement psFind = conn.prepareStatement(findIdSql)) {
                psFind.setString(1, email);
                try (ResultSet rs = psFind.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                    }
                }
            }


            if (userId != -1) {

                try (PreparedStatement ps1 = conn.prepareStatement(deleteStudentSql)) {
                    ps1.setInt(1, userId);
                    ps1.executeUpdate();
                }


                try (PreparedStatement ps2 = conn.prepareStatement(deleteUserSql)) {
                    ps2.setInt(1, userId);
                    ps2.executeUpdate();
                    System.out.println("Deleted successfully using ID: " + userId);
                }
            } else {

                try (PreparedStatement psBackup = conn.prepareStatement("DELETE FROM users WHERE email = ?")) {
                    psBackup.setString(1, email);
                    psBackup.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in deleteStudent:");
            e.printStackTrace();
        }
    }
    public boolean insertNotice(String route, String message) {
        String query = "INSERT INTO student_notices (route_id, message) VALUES (?, ?)";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, route);
            stmt.setString(2, message);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 }
