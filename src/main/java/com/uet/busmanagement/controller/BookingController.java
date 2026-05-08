package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Booking;
import java.util.ArrayList;

public class BookingController {

    private ArrayList<Booking> bookings = new ArrayList<>();

    public void createBooking(int bookingId, int studentId, String busNumber) {
        Booking b = new Booking(bookingId, studentId, busNumber);
        bookings.add(b);
        System.out.println("Booking Successful!");
    }

    public void showBookings() {
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }
}