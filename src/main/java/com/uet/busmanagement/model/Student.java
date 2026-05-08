package com.uet.busmanagement.model;

public class Student {

    private int studentId;
    private String name;
    private String department;
    private String regnum;

    // optional: assigned bus
    private String busNumber;

    public Student(int studentId, String name, String department, String regnum) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.regnum = regnum;
    }

    public void assignBus(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusNumber() {
        return busNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + studentId +
                ", Name='" + name + '\'' +
                ", Dept='" + department + '\'' +
                ", Roll='" + regnum + '\'' +
                ", Bus='" + (busNumber != null ? busNumber : "Not Assigned") + '\'' +
                '}';
    }
}