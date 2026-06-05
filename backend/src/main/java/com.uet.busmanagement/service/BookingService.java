package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Booking;
import com.uet.busmanagement.repository.BookingRepository;
import com.uet.busmanagement.repository.RouteRepository;
import com.uet.busmanagement.util.LogManager;

public class BookingService {
    private BookingRepository bookingRepo = new BookingRepository();
    private RouteRepository routeRepo = new RouteRepository();

    public String processBooking(String regNum, String routeId) {

        int capacity = routeRepo.getRouteCapacity(routeId);
        int currentBookings = bookingRepo.countBookingsForToday(routeId);
        if (currentBookings < capacity) {
            Booking booking = new Booking(regNum, routeId);
            boolean isSaved = bookingRepo.saveBooking(booking);
            return isSaved ? "Booking successful!" : "Database error!";
        } else {
            return "Capacity full!";
        }
    }
    public String cancelBooking(String regNum) {
        if (bookingRepo.deleteBooking(regNum)) {
            return "Booking cancelled successfully.";
        } else {
            return "No booking found for today to cancel.";
        }
    }
}