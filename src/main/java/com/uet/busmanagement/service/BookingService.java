package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Booking;
import com.uet.busmanagement.repository.BookingRepository;

public class BookingService
{

    private BookingRepository BookingRepository;

    public BookingService()
    {
        this.BookingRepository = new BookingRepository();
    }

    // 1. New seat booking ki logic
    public String bookSeat(Booking booking)
    {
        // Pehle check karo ke is student ne pehle se seat book to nahi ki hui
        if (BookingRepository.findByStudentId(booking.getStudentId()) != null)
        {
            return "Student already has an active booking!";
        }

        boolean isSaved = BookingRepository.save(booking);
        if (isSaved)
        {
            return "Seat booked successfully in Bus " + booking.getBusNumber() + "!";
        }
        else
        {
            return "Failed to book seat due to database error.";
        }
    }

    // 2. Booking cancellation ki logic via ID
    public String cancelBooking(int bookingId)
    {
        boolean isDeleted = BookingRepository.delete(bookingId);
        if (isDeleted)
        {
            return "Booking cancelled successfully!";
        }
        else
        {
            return "Failed to cancel booking. Booking ID not found.";
        }
    }

    // 3. Student ID ke zariye booking details check karna
    public Booking getBookingByStudent(int studentId)
    {
        return BookingRepository.findByStudentId(studentId);
    }
}