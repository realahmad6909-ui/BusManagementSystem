package com.uet.busmanagement.model;

import java.sql.Timestamp;

public class Booking {
    private int booking_id;
    private String regnum;
    private String route_id;
    private Timestamp booking_date;

    // Default Constructor
    public Booking() {}


    public Booking(String studentReg, String route_id) {
        this.regnum = studentReg;
        this.route_id = route_id;
    }

    // Getters and Setters
    public int getBookingid() { return booking_id; }
    public void setBookingid(int booking_id) { this.booking_id = booking_id; }

    public String getStudentReg() { return regnum; }
    public void setStudentReg(String studentReg) { this.regnum= regnum; }

    public String getRouteid() { return route_id; }
    public void setRouteid(String route_id) { this.route_id = route_id; }

    public Timestamp getBooking_date() { return booking_date; }
    public void setBooking_date(Timestamp booking_date) { this.booking_date = booking_date; }
}