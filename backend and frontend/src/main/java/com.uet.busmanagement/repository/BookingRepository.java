package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Booking;
import java.sql.*;

public class BookingRepository {

    public int countBookingsForToday(String routeId) {
        // FIXED: Table name changed from 'booking' to 'bookings'
        String query = "SELECT COUNT(*) FROM bookings WHERE route_id = ? AND DATE(booking_date) = CURDATE()";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, routeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error counting bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public boolean saveBooking(Booking booking) {
        // FIXED: Table name changed from 'booking' to 'bookings'
        String query = "INSERT INTO bookings (regnum, route_id, booking_date) VALUES (?, ?, NOW())";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Model getters check karlein (getRegnum aur getRoute_id)
            stmt.setString(1, booking.getStudentReg());
            stmt.setString(2, booking.getRouteid());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println(" SQL INSERTION CRASH: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(String regNum) {
        // FIXED: Table name changed from 'booking' to 'bookings'
        String query = "DELETE FROM bookings WHERE regnum = ? AND DATE(booking_date) = CURDATE()";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, regNum);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(" Error deleting booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}