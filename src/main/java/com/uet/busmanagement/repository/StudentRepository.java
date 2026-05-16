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

    // 1. Student ka record save karne ke liye
    public boolean save(Student student)
    {
        String query = "INSERT INTO students (student_id, name, department, regnum, bus_number) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getDepartment());
            stmt.setString(4, student.getRegnum());
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

    // 2. Student ko bus assign karne ya update karne ke liye
    public boolean updateBusAssignment(int studentId, String busNumber)
    {
        String query = "UPDATE students SET bus_number = ? WHERE student_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, busNumber);
            stmt.setInt(2, studentId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Database se saare students ki list fetch karne ke liye
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
                student.setName(rs.getString("name"));
                student.setDepartment(rs.getString("department"));
                student.setRegnum(rs.getString("regnum"));
                student.assignBus(rs.getString("bus_number"));

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