package com.uet.busmanagement.model;


public class Student extends User
{
    private int studentId;
    private String regNum;
    private String department;
    private int busid;
    private String status;
    private String email;

    public Student()
    {
        super();
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}