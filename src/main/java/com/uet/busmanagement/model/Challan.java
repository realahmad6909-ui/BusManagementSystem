package com.uet.busmanagement.model;

public class Challan
{

    private String challanNumber;
    private int studentId;
    private double amount;
    private String dueDate;
    private String status; // PAID, UNPAID, OVERDUE

    public Challan()
    {

    }

    // Parameterized Constructor
    public Challan(String challanNumber, int studentId, double amount, String dueDate, String status)
    {
        this.challanNumber = challanNumber;
        this.studentId = studentId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters and Setters
    public String getChallanNumber()
    {
        return challanNumber;
    }

    public void setChallanNumber(String challanNumber)
    {
        this.challanNumber = challanNumber;
    }

    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(String dueDate)
    {
        this.dueDate = dueDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Challan{" +
                "ChallanNo='" + challanNumber + '\'' +
                ", StudentID=" + studentId +
                ", Amount=" + amount +
                ", DueDate='" + dueDate + '\'' +
                ", Status='" + status + '\'' +
                '}';
    }
}