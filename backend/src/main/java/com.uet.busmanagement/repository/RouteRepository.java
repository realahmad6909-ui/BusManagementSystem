package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Route;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; // Yeh line miss hai
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteRepository {
    // Add Route
    public void addRoute(String id, String name) {
        String query = "INSERT INTO routes (route_id, route_name) VALUES (?, ?)";
        try (Connection conn = db.initializeDatabase(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id); ps.setString(2, name);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void getAllRoutes() {
        String query = "SELECT route_id, route_name FROM routes";
        try (Connection conn = db.initializeDatabase(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(query)) {
            System.out.println("ID \t| Route Name");
            while (rs.next()) {
                System.out.println(rs.getString("route_id") + " \t| " + rs.getString("route_name"));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    public int getRouteCapacity(String routeId) {
        // Hum route_id ke zariye us route par chalne wali tamam buses ki capacity sum kar rahe hain
        String query = "SELECT SUM(seating_capacity + standing_capacity) as total_capacity " +
                "FROM buses WHERE route_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, routeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_capacity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void deleteRoute(String id) {
        String query = "DELETE FROM routes WHERE route_id = ?";
        try (Connection conn = db.initializeDatabase(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}