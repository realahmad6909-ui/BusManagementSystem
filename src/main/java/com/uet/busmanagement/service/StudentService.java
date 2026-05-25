package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Student;
import com.uet.busmanagement.repository.StudentRepository;
import java.util.List;

public class StudentService
{

    private StudentRepository studentRepository;

    public StudentService()
    {
        this.studentRepository = new StudentRepository();
    }

    public String registerStudent(Student student)
    {
        // 1. Yahan variable ka naam 'studentRepository' (small s) hona chahiye
        // 2. 'findStudent' method ka naam bilkul sahi hona chahiye
        // 3. 'getRegNum()' mien R aur N capital hone chahiye
        if (studentRepository.findStudent(student.getRegNum()) != null)
        {
            return "Student with this Registration Number already exists!";
        }

        boolean isSaved = studentRepository.save(student);
        if (isSaved)
        {
            if (student.getBusNumber() != null && !student.getBusNumber().isEmpty())
            {
                return "Student registered and assigned to Bus " + student.getBusNumber() + " successfully!";
            }
            return "Student registered successfully without bus assignment!";
        }
        else
        {
            return "Failed to register student.";
        }
    }

    public Student getStudentDetails(String regNum)
    {
        return studentRepository.findStudent(regNum);
    }

    public Student getStudentDetails(int studentId)
    {
        return studentRepository.findStudent(studentId);
    }

    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }
}