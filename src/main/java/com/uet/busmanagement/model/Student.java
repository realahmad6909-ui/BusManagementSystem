package com.uet.busmanagement.model;

// Student ne User ko inherit kar liya, ab name, email automatic is ke paas hain
public class Student extends User
{
    private int studentId;
    private String regNum;
    private String department;
    private String busNumber;

    public Student()
    {
        super(); // Parent class ke constructor ko call kiya
    }

    // Getters and Setters sirf child ke variables ke liye
    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    public String getRegNum()
    {
        return regNum;
    }

    public void setRegNum(String regNum)
    {
        this.regNum = regNum;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getBusNumber()
    {
        return busNumber;
    }

    public void setBusNumber(String busNumber)
    {
        this.busNumber = busNumber;
    }
}