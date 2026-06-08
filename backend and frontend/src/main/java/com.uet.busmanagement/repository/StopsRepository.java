package com.uet.busmanagement.repository;
import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.model.Stops;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; // Yeh line miss hai
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StopsRepository {

    // 1. Add New Stop
    public void addStop(String routeId, String stopName, String arr, String dep) {
        String query = "INSERT INTO stops (route_id, stop_name, morning_arrival_time, afternoon_departure_time) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, routeId);
            ps.setString(2, stopName);
            ps.setString(3, arr);
            ps.setString(4, dep);

            ps.executeUpdate();
            System.out.println("Stop added successfully to Route: " + routeId);

        } catch (SQLException e) {
            System.out.println("Database Error (Add Stop): " + e.getMessage());
        }
    }

    // StopsRepository.java mein ye update karein
    public List<Stops> findByRouteId(String routeId) {
        List<Stops> stops = new ArrayList<>();
        String query = "SELECT * FROM stops WHERE route_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, routeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Stops stop = new Stops();
                stop.setStop_id(rs.getInt("stop_id"));

                stop.setRoute_id(rs.getString("route_id"));

                stop.setStop_name(rs.getString("stop_name"));
                stop.setMorning_arrival_time(rs.getString("morning_arrival_time"));
                stop.setAfternoon_departure_time(rs.getString("afternoon_departure_time"));
                stops.add(stop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stops;
    }
    // 3. Delete a specific Stop
    public void deleteStop(int stopId) {
        String query = "DELETE FROM stops WHERE stop_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, stopId);
            int rows = ps.executeUpdate();

            if (rows > 0) System.out.println("Stop ID " + stopId + " deleted successfully!");
            else System.out.println("Stop ID not found.");

        } catch (SQLException e) {
            System.out.println("Database Error (Delete Stop): " + e.getMessage());
        }
    }
}