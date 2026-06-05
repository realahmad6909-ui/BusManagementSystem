package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.service.BusService;
import java.util.List;
import java.util.Scanner;

public class BusController {

    private BusService busService;

    public BusController() {
        this.busService = new BusService();
    }

    private static Scanner sc = new Scanner(System.in);

    public String handleAddBus(int busId, String busNumber, String busName, int seatingCapacity, int standingCapacity, String id, String driverId, String status) {
        Bus bus = new Bus();
        bus.setBusId(busId);
        bus.setBusNumber(busNumber);
        bus.setBusName(busName);
        bus.setSeatingCapacity(seatingCapacity);
        bus.setStandingCapacity(standingCapacity);

        // OOP Association/Composition
        Route busRoute = new Route();
        busRoute.setRouteId(id);
        bus.setRoute(busRoute);
        Driver driver = new Driver();
        bus.setDriver(driver);
        bus.setStatus(status);

        return busService.addBus(bus);
    }

    public List<Bus> handleGetActiveBuses() {
        return busService.getActiveBuses();
    }

    public void handleDeleteBus() {
        System.out.print("Enter Bus Number to Delete: ");
        String busNumber = sc.next(); // User se input lein

        String result = busService.deleteBus(busNumber);
        System.out.println(result); // Result user ko dikhayein
    }

    public void manageBuses() {
        System.out.println("1. Add Bus | 2. View All Buses | 3. Delete Bus " );
        int ch = sc.nextInt();
        if (ch == 1) {
            System.out.print("Bus ID, Number, Name, seating Capacity, standing capacity,RouteID,Driver id,status ");
            handleAddBus(sc.nextInt(), sc.next(), sc.next(), sc.nextInt(), sc.nextInt(), sc.next(), sc.next(), "ACTIVE");
        }
        if (ch == 2) {
            handleGetActiveBuses();
        }
        if (ch == 3){
            handleDeleteBus();
        }
     }

}
