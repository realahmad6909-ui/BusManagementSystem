package com.uet.busmanagement.repository;

import com.uet.busmanagement.config.db;
import com.uet.busmanagement.model.Route;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteRepository
{

    // 1. Naya Route database mien save karne ke liye
    public boolean save(Route route)
    {
        String query = "INSERT INTO routes (route_id, source, destination, stops) VALUES (?, ?, ?, ?)";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, route.getRouteId());
            stmt.setString(2, route.getSource());
            stmt.setString(3, route.getDestination());

            // Stops ArrayList ko comma-separated String mien convert kar rahay hain
            String stopsString = (route.getStops() != null) ? String.join(",", route.getStops()) : "";
            stmt.setString(4, stopsString);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Route ID ke zariye specific Route dhoondne ke liye
    public Route findById(String routeId)
    {
        String query = "SELECT * FROM routes WHERE route_id = ?";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, routeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                Route route = new Route();
                route.setRouteId(rs.getString("route_id"));
                route.setSource(rs.getString("source"));
                route.setDestination(rs.getString("destination"));

                // Comma-separated string ko wapas ArrayList mien convert kar rahay hain
                String stopsString = rs.getString("stops");
                if (stopsString != null && !stopsString.trim().isEmpty())
                {
                    String[] stopsArray = stopsString.split(",");
                    route.setStops(new ArrayList<>(Arrays.asList(stopsArray)));
                }

                return route;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Saare Routes ki list database se fetch karne ke liye
    public List<Route> findAll()
    {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT * FROM routes";

        try (Connection conn = db.initializeDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next())
            {
                Route route = new Route();
                route.setRouteId(rs.getString("route_id"));
                route.setSource(rs.getString("source"));
                route.setDestination(rs.getString("destination"));

                String stopsString = rs.getString("stops");
                if (stopsString != null && !stopsString.trim().isEmpty())
                {
                    String[] stopsArray = stopsString.split(",");
                    route.setStops(new ArrayList<>(Arrays.asList(stopsArray)));
                }

                routes.add(route);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return routes;
    }
}