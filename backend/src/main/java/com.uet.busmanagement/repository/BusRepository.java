package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BusRepository
{
    private DriverRepository driverRepo = new DriverRepository();

    public boolean save(Bus bus)
    {
        // Query mein 8 placeholders hain
        String query = "INSERT INTO buses (bus_id, bus_number, bus_name, seating_capacity, standing_capacity, route_id, driver_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, bus.getBusId());
            stmt.setString(2, bus.getBusNumber());
            stmt.setString(3, bus.getBusName());
            stmt.setInt(4, bus.getSeatingCapacity());
            stmt.setInt(5, bus.getStandingCapacity());
            stmt.setString(6, (bus.getRoute() != null) ? bus.getRoute().getRouteId() : null);

            // Driver ID ko String mein convert karke 7th parameter par set kiya
            stmt.setString(7, (bus.getDriver() != null) ? String.valueOf(bus.getDriver().getUserId()) : null);

            // Status ko 8th parameter par set kiya
            stmt.setString(8, bus.getStatus() != null ? bus.getStatus() : "ACTIVE");

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public List<Bus> findActiveBuses()
    {
        List<Bus> buses = new ArrayList<>();

        // FIX: 'Available' hata kar direct saari buses mangwalo taaki Active aur Maintenance dono dekhain!
        String query = "SELECT * FROM buses";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next())
            {
                Bus bus = new Bus();
                bus.setBusId(rs.getInt("bus_id"));
                bus.setBusNumber(rs.getString("bus_number"));
                bus.setBusName(rs.getString("bus_name"));
                bus.setSeatingCapacity(rs.getInt("seating_capacity"));
                bus.setStandingCapacity(rs.getInt("standing_capacity"));

                Route route = new Route();
                route.setRouteId(rs.getString("route_id"));
                bus.setRoute(route);

                String dId = rs.getString("driver_id");
                if (dId != null && !dId.isEmpty()) {
                    try {
                        int driverIdInt = Integer.parseInt(dId);
                        Driver driver = driverRepo.findDriverById(driverIdInt);
                        bus.setDriver(driver);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Driver ID is not a valid number: " + dId);
                    }
                }
                bus.setStatus(rs.getString("status"));
                buses.add(bus);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return buses;
    }
    public boolean delete(String busNumber) {
        String query = "DELETE FROM buses WHERE bus_number = ?";
        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, busNumber);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}