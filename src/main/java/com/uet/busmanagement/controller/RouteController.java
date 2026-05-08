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

    public String displayAllRoutes() {
        String result = "";

        for (Route r : routes) {
            result += r.toString() + "\n";
        }
        return result;
    }
}