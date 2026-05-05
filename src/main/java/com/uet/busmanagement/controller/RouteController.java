package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Route;
import java.util.ArrayList;

public class RouteController {

    private ArrayList<Route> routes;

    public RouteController()
    {
        routes = new ArrayList<>();
    }

    public void addRoute(Route route)
    {
        routes.add(route);
        System.out.println("Route added successfully!");
    }

    public void displayAllRoutes()
    {
        for (Route r : routes) {
            r.displayRoute();
            System.out.println("-------------------");
        }
    }
}