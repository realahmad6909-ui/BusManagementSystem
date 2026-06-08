//package com.uet.busmanagement.controller;
//
//import com.uet.busmanagement.model.Bus;
//import com.uet.busmanagement.model.Route;
//import com.uet.busmanagement.model.Driver;
//import com.uet.busmanagement.service.BusService;
//import java.util.List;
//import java.util.Scanner;
//
//public class BusController {
//
//    private BusService busService;
//
//    public BusController() {
//        this.busService = new BusService();
//    }
//
//    private static Scanner sc = new Scanner(System.in);
//
//    public String handleAddBus(int busId, String busNumber, String busName, int seatingCapacity, int standingCapacity, String id, String driverId, String status) {
//        Bus bus = new Bus();
//        bus.setBusId(busId);
//        bus.setBusNumber(busNumber);
//        bus.setBusName(busName);
//        bus.setSeatingCapacity(seatingCapacity);
//        bus.setStandingCapacity(standingCapacity);
//
//        // OOP Association/Composition
//        Route busRoute = new Route();
//        busRoute.setRouteId(id);
//        bus.setRoute(busRoute);
//        Driver driver = new Driver();
//        bus.setDriver(driver);
//        bus.setStatus(status);
//
//        return busService.addBus(bus);
//    }
//
//    public List<Bus> handleGetActiveBuses() {
//        return busService.getActiveBuses();
//    }
//
//    public void handleDeleteBus() {
//        System.out.print("Enter Bus Number to Delete: ");
//        String busNumber = sc.next(); // User se input lein
//
//        String result = busService.deleteBus(busNumber);
//        System.out.println(result); // Result user ko dikhayein
//    }
//
//    public void manageBuses() {
//        System.out.println("1. Add Bus | 2. View All Buses | 3. Delete Bus " );
//        int ch = sc.nextInt();
//        if (ch == 1) {
//            System.out.print("Bus ID, Number, Name, seating Capacity, standing capacity,RouteID,Driver id,status ");
//            handleAddBus(sc.nextInt(), sc.next(), sc.next(), sc.nextInt(), sc.nextInt(), sc.next(), sc.next(), "ACTIVE");
//        }
//        if (ch == 2) {
//            handleGetActiveBuses();
//        }
//        if (ch == 3){
//            handleDeleteBus();
//        }
//     }
//
//}

package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.service.BusService;
import org.springframework.stereotype.Controller; // RestController se CONTROLLER kar diya
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class BusController {

    private final BusService busService = new BusService();

    // --- 1. VIEW ALL BUSES (Thymeleaf UI Table loading) ---
    @GetMapping("/buses")
    public String showManageBusesPage(Model model) {
        // Aapki service se saari active buses ki list mangwai
        List<Bus> activeBuses = busService.getActiveBuses();

        // Thymeleaf HTML ko data pass kiya
        model.addAttribute("buses", activeBuses);
        return "manage-buses"; // templates/manage-buses.html load hoga
    }

    // --- 2. ADD NEW BUS (HTML Form submission handling) ---
    @PostMapping("/buses/add")
    public String handleAddBus(@RequestParam int busId,
                               @RequestParam String busNumber,
                               @RequestParam String busName,
                               @RequestParam int seatingCapacity,
                               @RequestParam int standingCapacity,
                               @RequestParam String routeId,
                               @RequestParam String driverId,
                               @RequestParam String status) {

        Bus bus = new Bus();
        bus.setBusId(busId);
        bus.setBusNumber(busNumber);
        bus.setBusName(busName);
        bus.setSeatingCapacity(seatingCapacity);
        bus.setStandingCapacity(standingCapacity);

        Route busRoute = new Route();
        busRoute.setRouteId(routeId);
        bus.setRoute(busRoute);

        Driver driver = new Driver();
        if (driverId != null && !driverId.isEmpty()) {
            driver.setUserId(Integer.parseInt(driverId));
        }
        bus.setDriver(driver);
        bus.setStatus(status);


        busService.addBus(bus);

        // Save hone ke baad page refresh karne ke liye redirect
        return "redirect:/buses";
    }

    // --- 3. BUS DELETE KARNE KE LIYE (Query string dynamic trigger) ---
    @GetMapping("/buses/delete")
    public String handleDeleteBus(@RequestParam String busNumber) {
        if (busNumber != null && !busNumber.trim().isEmpty()) {
            busService.deleteBus(busNumber);
        }
        return "redirect:/buses";
    }
}