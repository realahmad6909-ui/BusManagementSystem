package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository
{

    // 1. Nayi Seat Booking database mien save karne ke liye
    public boolean save(Booking booking)
    {
        String query = "INSERT INTO bookings (booking_id, student_id, bus_number) VALUES (?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, booking.getBookingId());
            stmt.setInt(2, booking.getStudentId());
            stmt.setString(3, booking.getBusNumber());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Student ID ke zariye check karne ke liye ke uski koi booking hai ya nahi
    public Booking findByStudentId(int studentId)
    {
        String query = "SELECT * FROM bookings WHERE student_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setStudentId(rs.getInt("student_id"));
                booking.setBusNumber(rs.getString("bus_number"));
                return booking;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Booking cancel (delete) karne ke liye
    public boolean delete(int bookingId)
    {
        String query = "DELETE FROM bookings WHERE booking_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, bookingId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}