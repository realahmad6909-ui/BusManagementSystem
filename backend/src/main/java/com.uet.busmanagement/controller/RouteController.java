package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.service.RouteService;
import com.uet.busmanagement.controller.StopsController;

import java.util.Scanner;

public class RouteController {
    private RouteService routeService;
    private static Scanner sc = new Scanner(System.in);
    private static StopsController stopctrl = new StopsController();

    public RouteController() {
        this.routeService = new RouteService();
    }

    public void handleAddRoute(String id, String name) {
        if (id.trim().isEmpty() || name.trim().isEmpty()) {
            System.out.println("Route ID and Name cannot be empty!");
        }
        routeService.addRoute(id, name);
    }

    public void displayallroutes() {
        routeService.displayAllRoutes();
    }

    public void deleteroute(String id) {
        routeService.removeRoute(id);
    }

    public void handleShowRouteDetails(String routeId) {
        new StopsController().handleViewStops(routeId);
    }

    public void manageRoutes() {
        System.out.println("1. Add Route | 2. Delete Route | 3. Display All Routes | 4. Add Stops ");
        int ch = sc.nextInt();
        if (ch == 1) {
            System.out.println("Route-id,Route Name");
            handleAddRoute(sc.next(),sc.next());
        }
        if (ch == 3) {
            displayallroutes();
        }
        if (ch == 2) {
            System.out.println("Route-ID");
            deleteroute(sc.next());
        }
        if (ch == 4) {
            System.out.println("Route ID, NAME, ARRIVAL MORNING TIME, AFTERNOON DEPARTURE TIME");
            stopctrl.handleAddStop(sc.next(),sc.next(),sc.next(),sc.next());
        }
    }
}

