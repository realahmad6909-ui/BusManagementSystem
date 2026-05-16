package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Route;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BusRepository
{

    // 1. Nayi Bus ka record database mien save karne ke liye
    public boolean save(Bus bus)
    {
        String query = "INSERT INTO buses (bus_id, bus_number, bus_name, seating_capacity, standing_capacity, route_id, driver_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, bus.getBusId());
            stmt.setString(2, bus.getBusNumber());
            stmt.setString(3, bus.getBusName());
            stmt.setInt(4, bus.getSeatingCapacity());
            stmt.setInt(5, bus.getStandingCapacity());
            // Agar route assigned hai to uski ID rkhnge, warna null
            stmt.setString(6, (bus.getRoute() != null) ? bus.getRoute().getRouteId() : null);
            stmt.setString(7, bus.getDriverId());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Bus ka Route update ya assign karne ke liye
    public boolean updateBusRoute(String busNumber, String routeId)
    {
        String query = "UPDATE buses SET route_id = ? WHERE bus_number = ?";

        try (Connection conn =db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, routeId);
            stmt.setString(2, busNumber);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Database se saari buses ki list fetch karne ke liye
    public List<Bus> findAll()
    {
        List<Bus> buses = new ArrayList<>();
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
                bus.setDriverId(rs.getString("driver_id"));

                // Route ki mapping hum shallow object bana kar karletay hain
                String routeId = rs.getString("route_id");
                if (routeId != null)
                {
                    Route route = new Route();
                    route.setRouteId(routeId);
                    bus.setRoute(route);
                }

                buses.add(bus);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return buses;
    }
}