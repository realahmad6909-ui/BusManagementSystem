package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.repository.RouteRepository;
import com.uet.busmanagement.repository.StopsRepository;
import com.uet.busmanagement.util.LogManager;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RouteService {

    private RouteRepository routeRepo = new RouteRepository();
    private StopsRepository stopRepo = new StopsRepository();

    public void addRoute(String id, String name, String URL) {
        if (id == null || id.isEmpty()) {
            System.out.println("Route ID cannot be empty!");
            return;
        }
        routeRepo.addRoute(id, name,URL);
    }
    public List<String> getAllRouteIdsForDropdown() {
        return routeRepo.getAllRouteIds();
    }
    public List displayAllRoutes() {
        System.out.println("\n--- LIST OF ALL ROUTES ---");
        return routeRepo.getAllRoutesFromDB();
    }

    public void removeRoute(String id) {
        routeRepo.deleteRoute(id);
    }

    public void showRouteDetails(String routeId) {
        System.out.println("\nFetching schedule for: " + routeId);
        stopRepo.findByRouteId(routeId);
    }
}
