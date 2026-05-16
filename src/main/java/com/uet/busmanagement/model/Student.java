package com.uet.busmanagement.model;

public class Student {

    private int studentId;
    private String name;
    private String department;
    private String regnum;
    private String busNumber;


    public Student()
    {
    }

    // Parameterized Constructor
    public Student(int studentId, String name, String department, String regnum)
    {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.regnum = regnum;
    }

    //  Getters and Setters
    public int getStudentId()
    {
        return studentId;
    }
    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getDepartment()
    {
        return department;
    }
    public void setDepartment(String department)
    {
        this.department = department;
    }
    public String getRegnum()
    {
        return regnum;
    }
    public void setRegnum(String regnum)
    {
        this.regnum = regnum;
    }
    public void assignBus(String busNumber)
    {
        this.busNumber = busNumber;
    }
    public String getBusNumber()
    {
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