package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.repository.RouteRepository;
import com.uet.busmanagement.repository.StopsRepository;
import com.uet.busmanagement.util.LogManager;
import java.util.List;



public class RouteService {

    private RouteRepository routeRepo = new RouteRepository();
    private StopsRepository stopRepo = new StopsRepository();

    public void addRoute(String id, String name) {
        if (id == null || id.isEmpty()) {
            System.out.println("Route ID cannot be empty!");
            return;
        }
        routeRepo.addRoute(id, name);
    }

    public void displayAllRoutes() {
        System.out.println("\n--- LIST OF ALL ROUTES ---");
        routeRepo.getAllRoutes();
    }

    public void removeRoute(String id) {
        routeRepo.deleteRoute(id);
    }

    public void showRouteDetails(String routeId) {
        System.out.println("\nFetching schedule for: " + routeId);
        stopRepo.findByRouteId(routeId);
    }
}
