package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Stops;
import com.uet.busmanagement.repository.StopsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopsService {

    private StopsRepository stopRepo = new StopsRepository();


    public void addStopToRoute(String routeId, String stopName, String arrival, String departure) {
        if (stopName == null || stopName.isEmpty()) {
            System.out.println("Stop name cannot be empty!");
            return;
        }
        stopRepo.addStop(routeId, stopName, arrival, departure);
    }

    public void showStopsByRoute(String routeId) {
        System.out.println("\n--- Displaying Schedule for Route: " + routeId + " ---");
        stopRepo.findByRouteId(routeId);
    }


    public void deleteStop(int stopId) {
        stopRepo.deleteStop(stopId);
    }


    public List<Stops> getStopsByRouteId(String routeId) {
        if (routeId == null || routeId.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        return stopRepo.findByRouteId(routeId);
    }
}