package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Student;
import java.util.ArrayList;

public class StudentController {

    private ArrayList<Student> students = new ArrayList<>();


    public void addStudent(Student s)
    {
        students.add(s);
        System.out.println("Student added!");
    }

    public void assignBus(int studentId, String busNumber) {
        for (Student s : students) {
            if (s.toString().contains("ID=" + studentId)) {
                s.assignBus(busNumber);
                System.out.println("Bus assigned!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void displayStudents() {
        for (Student s : students) {
            System.out.println(s);
        }
    }
}