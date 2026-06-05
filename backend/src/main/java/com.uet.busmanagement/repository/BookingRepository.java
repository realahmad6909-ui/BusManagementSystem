package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Booking;
import java.sql.*;

public class BookingRepository {

    public int countBookingsForToday(String routeId) {
        String query = "SELECT COUNT(*) FROM bookings WHERE route_id = ? AND DATE(booking_date) = CURDATE()";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, routeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean saveBooking(Booking booking) {
        String query = "INSERT INTO bookings (student_reg, route_id) VALUES (?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, booking.getStudentReg());
            stmt.setString(2, booking.getRouteid());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteBooking(String regNum) {
        // Sirf aaj ki booking delete hogi
        String query = "DELETE FROM bookings WHERE student_reg = ? AND DATE(booking_date) = CURDATE()";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, regNum);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}