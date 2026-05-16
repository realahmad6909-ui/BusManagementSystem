package com.uet.busmanagement.model;

import java.util.ArrayList;
import java.util.List;
import com.uet.busmanagement.model.Bus;

public class Route {

    private String routeId;
    private List<Bus> buses = new ArrayList<>();
    private String source;
    private String destination;
    private ArrayList<String> stops;

    public Route()
    {
        this.buses = new ArrayList<>();
        this.stops = new ArrayList<>();
    }

    //  Parameterized Constructor
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

    public void addBus(Bus bus)
    {
        buses.add(bus);
    }

    public void displayRoute()
    {
        System.out.println("Route: " + source + " → " + destination);
        System.out.println("Stops: " + stops);
    }

    public String displayBuses()
    {
        String result = "Route " + routeId + " Buses:\n";
        for (Bus b : buses)
        {
            result += b.toString() + "\n";
        }
        return result;
    }

    // 4. Getters and Setters
    public String getRouteId()
    {
        return routeId;
    }

    public void setRouteId(String routeId)
    {
        this.routeId = routeId;
    }

    public List<Bus> getBuses()
    {
        return buses;
    }

    public void setBuses(List<Bus> buses)
    {
        this.buses = buses;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public ArrayList<String> getStops()
    {
        return stops;
    }

    public void setStops(ArrayList<String> stops)
    {
        this.stops = stops;
    }

    @Override
    public String toString() {
        return "Route{" +
                "ID='" + routeId + '\'' +
                ", Source='" + source + '\'' +
                ", Destination='" + destination + '\'' +
                ", StopsCount=" + (stops != null ? stops.size() : 0) +
                '}';
    }
}