package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Complaint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintRepository {

    public boolean saveComplaint(Complaint complaint) {

        String query = "INSERT INTO complaints (regnum, route_id, status, complaint_text) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, complaint.getRegnum());
            stmt.setString(2, complaint.getRouteId());
            stmt.setString(3, "PENDING"); // Direct status confirmation

            stmt.setString(4, "Bus Capacity Full - Request Extra Bus (Standing Included)");

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println(" DATABASE COMPLAINT INSERTION CRASH: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean markComplaintsAsResolved(String routeId) {
        String query = "UPDATE complaints SET status = 'RESOLVED' WHERE route_id = ? AND status = 'PENDING'";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, routeId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Complaint> getPendingComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT * FROM complaints WHERE status = 'PENDING'";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // FIXED: Agar table me column 'regnum' hai to rs.getString("regnum") aega
                Complaint c = new Complaint(rs.getString("regnum"), rs.getString("route_id"));
                c.setStatus(rs.getString("status"));
                complaints.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }
}