package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository
{

    public boolean save(Student student)
    {
        String query = "INSERT INTO students (student_id, reg_num, name, department, bus_number) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, student.getRegNum());
            stmt.setString(3, student.getName()); // Inherited from User
            stmt.setString(4, student.getDepartment());
            stmt.setString(5, student.getBusNumber());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // POLYMORPHISM FORM 1: Search via Registration Number (String)
    public Student findStudent(String regNum)
    {
        String query = "SELECT * FROM students WHERE reg_num = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, regNum);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setRegNum(rs.getString("reg_num"));
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department"));
                student.setBusNumber(rs.getString("bus_number"));
                return student;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // POLYMORPHISM FORM 2: Search via Student ID (int) -> Method Name is SAME!
    public Student findStudent(int studentId)
    {
        String query = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setRegNum(rs.getString("reg_num"));
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department"));
                student.setBusNumber(rs.getString("bus_number"));
                return student;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> findAll()
    {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next())
            {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setRegNum(rs.getString("reg_num"));
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department"));
                student.setBusNumber(rs.getString("bus_number"));

                students.add(student);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return students;
    }
}