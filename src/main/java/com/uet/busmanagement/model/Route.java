package com.uet.busmanagement.model;

import java.util.ArrayList;

public class Route {

    private String routeId;
    private String source;
    private String destination;
    private ArrayList<String> stops;

    public Route(String routeId, String source, String destination)
    {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.stops = new ArrayList<>();
    }

    public void addStop(String stop)
    {
        stops.add(stop);
    }

    public void displayRoute()
    {
        System.out.println("Route: " + source + " → " + destination);
        System.out.println("Stops: " + stops);
    }

    public String getRouteId() {
        return routeId;
    }
}