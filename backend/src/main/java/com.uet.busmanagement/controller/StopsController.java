package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Stops;
import com.uet.busmanagement.service.StopsService;
import com.uet.busmanagement.repository.StopsRepository;

import java.util.List;

public class StopsController {
    private StopsService stopService;
    private StopsRepository stopsRepo = new StopsRepository();

    public StopsController() {
        this.stopService = new StopsService();
    }

    public String handleAddStop(String routeId, String stopName, String arrival, String departure) {
        if (routeId == null || stopName == null || arrival == null || departure == null) {
            return "All fields are required for a stop!";
        }

        stopService.addStopToRoute(routeId, stopName, arrival, departure);
        return "Stop added successfully to Route: " + routeId;
    }

    public void handleViewStops(String routeId) {

        List<Stops> stops = stopsRepo.findByRouteId(routeId);

        if (stops.isEmpty()) {
            System.out.println("No stops found for this route.");
            return;
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-10s%n", "Stop ID", "Stop Name", "Arrival", "Departure");
        System.out.println("------------------------------------------------------------");

        // 3. For-each loop se table body print karein
        for (Stops stop : stops) {
            System.out.printf("%-10s | %-20s | %-10s | %-10s%n",
                    stop.getStop_id(),
                    stop.getStop_name(),
                    stop.getMorning_arrival_time(),
                    stop.getAfternoon_departure_time());
        }
        System.out.println("------------------------------------------------------------");
    }
}