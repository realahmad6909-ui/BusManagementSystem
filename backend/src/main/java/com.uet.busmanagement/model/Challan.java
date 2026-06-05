package com.uet.busmanagement.model;

public class Challan
{
    private String challanNumber;
    private int studentId;
    private String regnum;
    private double amount;
    private String dueDate;
    private String status;
    private String issueDate; // Advanced: Kab challan generate hua tha
    private double fine;      // Advanced: Due date guzarne par kitna fine laga

    public Challan()
    {
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

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
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

    public String getIssueDate()
    {
        return issueDate;
    }

    public void setIssueDate(String issueDate)
    {
        this.issueDate = issueDate;
    }

    public double getFine()
    {
        return fine;
    }

    public void setFine(double fine)
    {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "Challan{" +
                "challanNumber='" + challanNumber + '\'' +
                ", studentId=" + studentId +
                ", amount=" + amount +
                ", dueDate='" + dueDate + '\'' +
                ", status='" + status + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", fine=" + fine +
                '}';
    }
}