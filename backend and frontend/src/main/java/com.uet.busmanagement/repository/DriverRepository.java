package com.uet.busmanagement.repository;
import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.model.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    public Driver findDriverByEmail(String email) {
        String query = "SELECT d.driver_id, d.driver_name, d.license_number, d.phone_number, d.status, u.email " +
                "FROM drivers d " +
                "JOIN users u ON d.driver_id = u.user_id " +
                "WHERE u.email = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Driver driver = mapRowToDriver(rs);
                driver.setEmail(rs.getString("email"));
                return driver;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addDriver(Driver driver) {
        String userSql = "INSERT INTO users (name, email, password, role, phone) VALUES (?, ?, ?, 'DRIVER', ?)";
        String driverSql = "INSERT INTO drivers (driver_id, driver_name, license_number, phone_number) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase()) {
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, driver.getName());
            ps1.setString(2, driver.getEmail());
            ps1.setString(3, driver.getPassword());
            ps1.setString(4, driver.getPhone());
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                PreparedStatement ps2 = conn.prepareStatement(driverSql);
                ps2.setInt(1, userId);
                ps2.setString(2, driver.getName());
                ps2.setString(3, driver.getLicenseNumber());
                ps2.setString(4, driver.getPhone());
                ps2.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Bus getBusByDriverId(int driverId) {
        String query = "SELECT b.bus_number, r.route_name, r.route_id, r.map_url FROM buses b " +
                "JOIN routes r ON b.route_id = r.route_id WHERE b.driver_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bus bus = new Bus();
                bus.setBusNumber(rs.getString("bus_number"));

                Route route = new Route();
                route.setRouteName(rs.getString("route_name"));
                route.setRouteId(rs.getString("route_id"));
                route.setMapUrl(rs.getString("map_url")); // 👈 Yeh line database se link nikalegi

                bus.setRoute(route);
                return bus;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    public boolean saveSOSAlert(int driverId, String busNumber) {
        String query = "INSERT INTO transport_alerts (driver_id, bus_number) VALUES (?, ?)";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, driverId);
            ps.setString(2, busNumber);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<java.util.Map<String, Object>> getActiveAlerts() {
        List<java.util.Map<String, Object>> alertsList = new java.util.ArrayList<>();
        // Hum srf PENDING alerts nikal rahe hain taake resolved alerts gayab ho jayen
        String query = "SELECT id, driver_id, bus_number, alert_type, timestamp FROM transport_alerts WHERE status = 'PENDING' ORDER BY timestamp DESC";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                java.util.Map<String, Object> alert = new java.util.HashMap<>();
                alert.put("id", rs.getInt("id"));
                alert.put("driverId", rs.getInt("driver_id"));
                alert.put("busNumber", rs.getString("bus_number"));
                alert.put("alertType", rs.getString("alert_type"));
                alert.put("timestamp", rs.getTimestamp("timestamp").toString());
                alertsList.add(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alertsList;
    }
    public boolean resolveAlert(int alertId) {
        String query = "UPDATE transport_alerts SET status = 'RESOLVED' WHERE id = ?";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, alertId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Driver findDriverById(int driverId) {
        String query = "SELECT d.driver_id, d.driver_name, d.license_number, d.phone_number, d.status " +
                "FROM drivers d WHERE d.driver_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToDriver(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void deleteDriver(int driverId) {
        String query = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, driverId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean updateStatus(int driverId, String status) {
        String query = "UPDATE drivers SET status = ? WHERE driver_id = ?";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, driverId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Driver> findAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT driver_id, driver_name, license_number, phone_number, status FROM drivers";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                drivers.add(mapRowToDriver(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return drivers;
    }

    private Driver mapRowToDriver(ResultSet rs) throws SQLException {
        Driver driver = new Driver();
        driver.setUserId(rs.getInt("driver_id"));
        driver.setName(rs.getString("driver_name"));
        driver.setLicenseNumber(rs.getString("license_number"));
        driver.setStatus(rs.getString("status"));
        driver.setPhone(rs.getString("phone_number"));
        return driver;
    }
}