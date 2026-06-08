package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Student;
import com.uet.busmanagement.repository.StudentRepository;
import com.uet.busmanagement.util.LogManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService
{

    private StudentRepository studentRepository;

    public StudentService()
    {
        this.studentRepository = new StudentRepository();
    }


        public String registerStudent(Student student) {

            if (student.getEmail() == null || !student.getEmail().contains("@")) {
                return "Error: Invalid Email format!";
            }

            if (student.getPassword().length() < 6) {
                return "Error: Password must be at least 6 characters long!";
            }

            try {
                studentRepository.registerstudent(student);
                return "Success: Student " + student.getName() + " registered successfully!";
            } catch (Exception e) {
                return "Error: Registration failed - " + e.getMessage();
            }
        }
    public String removeStudent(String email) {
        try {
            studentRepository.deleteStudent(email);
            return "Student with email " + email + " removed successfully.";
        } catch (Exception e) {
            return "Error: Could not remove student.";
        }
    }

    public Student getStudentProfileById(int studentId)
    {
        return studentRepository.findStudent(studentId);
    }
    public Student getStudentProfileByregnum(String regNum)
    {
        return studentRepository.findStudentbyregnumber(regNum);
    }


    public List<Student> getAllStudentProfiles()
    {
        return studentRepository.findAll();
    }
    public List<Student> getPendingStudents() {
        return studentRepository.findAll().stream()
                .filter(s -> "PENDING".equals(s.getStatus()))
                .toList();
    }
    public String getNoticeForStudentDashboard(String studentRoute) {
        return studentRepository.getLatestNoticeForRoute(studentRoute);
    }
    public boolean changeStatus(int studentId, String status) {

        Student s = studentRepository.findStudent(studentId);
        if (s != null) {
            return studentRepository.updateStatus(studentId, status);
        }
        return false;
    }
    public Student login(String email, String password) {
        return studentRepository.findByEmailAndPassword(email, password);
    }
    // StudentService.java
    public String updateProfile(Student student) {
        if (studentRepository.updateStudentInfo(student)) {
            return "Profile updated successfully!";
        }
        return "Failed to update profile.";
    }
    public boolean saveAdminNotice(String route, String message) {

        return studentRepository.insertNotice(route, message);
    }
}
