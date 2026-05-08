package com.uet.busmanagement.model;

public class Booking
{

    private int bookingId;
    private int studentId;
    private String busNumber;

    public Booking(int bookingId, int studentId, String busNumber)
    {
        this.bookingId = bookingId;
        this.studentId = studentId;
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