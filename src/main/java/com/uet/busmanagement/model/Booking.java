package com.uet.busmanagement.model;

public class Booking
{
    private int bookingId;
    private int studentId;
    private String busNumber;

    public Booking()
    {

    }

    // Parameterized Constructor
    public Booking(int bookingId, int studentId, String busNumber)
    {
        this.bookingId = bookingId;
        this.studentId = studentId;
        this.busNumber = busNumber;
    }
    // 3. Getters and Setters

    public int getBookingId()
    {
        return bookingId;
    }

    public void setBookingId(int bookingId)
    {
        this.bookingId = bookingId;
    }

    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    public String getBusNumber()
    {
        return busNumber;
    }

    public void setBusNumber(String busNumber)
    {
        this.busNumber = busNumber;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "ID=" + bookingId +
                ", Student=" + studentId +
                ", Bus='" + busNumber + '\'' +
                '}';
    }
}