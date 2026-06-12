//package com.uet.busmanagement.controller;
//
//import com.uet.busmanagement.model.Stops;
//import com.uet.busmanagement.service.StopsService;
//import com.uet.busmanagement.repository.StopsRepository;
//
//import java.util.List;
//
//public class StopsController {
//    private StopsService stopService;
//    private StopsRepository stopsRepo = new StopsRepository();
//
//    public StopsController() {
//        this.stopService = new StopsService();
//    }
//
//    public String handleAddStop(String routeId, String stopName, String arrival, String departure) {
//        if (routeId == null || stopName == null || arrival == null || departure == null) {
//            return "All fields are required for a stop!";
//        }
//
//        stopService.addStopToRoute(routeId, stopName, arrival, departure);
//        return "Stop added successfully to Route: " + routeId;
//    }
//
//    public void handleViewStops(String routeId) {
//
//        List<Stops> stops = stopsRepo.findByRouteId(routeId);
//
//        if (stops.isEmpty()) {
//            System.out.println("No stops found for this route.");
//            return;
//        }
//
//        System.out.println("\n------------------------------------------------------------");
//        System.out.printf("%-10s | %-20s | %-10s | %-10s%n", "Stop ID", "Stop Name", "Arrival", "Departure");
//        System.out.println("------------------------------------------------------------");
//
//        // 3. For-each loop se table body print karein
//        for (Stops stop : stops) {
//            System.out.printf("%-10s | %-20s | %-10s | %-10s%n",
//                    stop.getStop_id(),
//                    stop.getStop_name(),
//                    stop.getMorning_arrival_time(),
//                    stop.getAfternoon_departure_time());
//        }
//        System.out.println("------------------------------------------------------------");
//    }
//}
package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Stops;
import com.uet.busmanagement.service.StopsService;
import com.uet.busmanagement.repository.StopsRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stops")
@CrossOrigin(origins = "*") // Taaki frontend is API ko call kar sake
public class StopsController {

    private StopsService stopService;
    private StopsRepository stopsRepo = new StopsRepository();

    public StopsController() {
        this.stopService = new StopsService();
    }

    // --- 1. NAYA STOP ADD KARNE KE LIYE (POST REQUEST) ---
    // URL: http://localhost:8080/api/stops/add
    @PostMapping("/add")
    public String handleAddStop(@RequestParam String routeId,
                                @RequestParam String stopName,
                                @RequestParam String arrival,
                                @RequestParam String departure) {

        if (routeId == null || stopName == null || arrival == null || departure == null) {
            return "All fields are required for a stop!";
        }

        stopService.addStopToRoute(routeId, stopName, arrival, departure);
        return "Stop added successfully to Route: " + routeId;
    }

    // --- 2. KISI KHAS ROUTE KE STOPS DIKHANE KE LIYE (GET REQUEST) ---
    // URL: http://localhost:8080/api/stops/route/{routeId}
    @GetMapping("/route/{routeId}")
    public List<Stops> handleViewStops(@PathVariable String routeId) {
        // Yeh database se us route ke saare stops ki list nikalega
        List<Stops> stops = stopsRepo.findByRouteId(routeId);

        // Direct list return karwa di taaki Spring ise JSON bana kar browser me dikhaye
        return stops;
    }
}