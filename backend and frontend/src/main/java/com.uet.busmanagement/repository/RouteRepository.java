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

    public void addRoute(String id, String name, String mapUrl) {
        String query = "INSERT INTO routes (route_id, route_name, map_url) VALUES (?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, mapUrl); // 👈 Teesri value mapUrl ki set kar di

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllRouteIds() {
        List<String> routesList = new ArrayList<>();
        String query = "SELECT route_id FROM routes";


        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                routesList.add(rs.getString("route_id"));
            }
            System.out.println("🗺️ [Repository]: Fetched " + routesList.size() + " routes from database.");
        } catch (Exception e) {
            System.out.println("🚨 Error in Repository getAllRouteIds(): " + e.getMessage());
            e.printStackTrace();
        }
        return routesList;
    }
    public List<Route> getAllRoutesFromDB() {
        List<Route> list = new ArrayList<>();
        String query = "SELECT * FROM routes";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Route route = new Route();
                route.setRouteId(rs.getString("route_id"));
                route.setRouteName(rs.getString("route_name"));
                route.setMapUrl(rs.getString("map_url"));
                list.add(route);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // Yeh pure records ki list return karega
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