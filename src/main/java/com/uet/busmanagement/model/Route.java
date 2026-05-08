package com.uet.busmanagement.model;
import java.util.ArrayList;
import java.util.List;

public class Route {

    private String routeId;
    private List<Bus> buses = new ArrayList<>();
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
    public void addBus(Bus bus) {
        buses.add(bus);
    }
    public String displayBuses() {
        String result = "Route " + routeId + " Buses:\n";

        for (Bus b : buses) {
            result += b.toString() + "\n";
        }
        return result;
    }
    @Override
    public String toString() {
        return "Route{" +
                "ID='" + routeId + '\'' +
                ", Start='" + source + '\'' +
                ", End='" + destination + '\'' +
                '}';
    }
}