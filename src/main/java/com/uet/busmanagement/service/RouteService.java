package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.repository.RouteRepository;
import java.util.List;

public class RouteService
{

    private RouteRepository RouteRepository;

    public RouteService()
    {
        this.RouteRepository = new RouteRepository();
    }

    // 1. Naya Route system mien add karne ki logic
    public String addRoute(Route route)
    {
        if (RouteRepository.findById(route.getRouteId()) != null)
        {
            return "Route with this ID already exists!";
        }

        boolean isSaved = RouteRepository.save(route);
        if (isSaved)
        {
            return "Route added successfully!";
        }
        else
        {
            return "Failed to add route.";
        }
    }

    // 2. Specific Route ID se details nikalna
    public Route getRouteById(String routeId)
    {
        return RouteRepository.findById(routeId);
    }

    // 3. Saare available routes ki list dekhna
    public List<Route> getAllRoutes()
    {
        return RouteRepository.findAll();
    }
}