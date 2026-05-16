package com.uet.busmanagement.model;

import com.uet.busmanagement.model.Route;

public class Bus {

    private int busId;
    private String busNumber;
    private String busName;
    private int seatingCapacity;
    private int standingCapacity;
    private Route route;
    private String driverId;

    public Bus()
    {

    }
    //  Parameterized Constructor
    public Bus(int busId, String busNumber, String busName, int seatingCapacity, int standingCapacity, Route route, String driverId)
    {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busName = busName;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.route = route;
        this.driverId = driverId;
    }

    public int getTotalCapacity()
    {
        return seatingCapacity + standingCapacity;
    }

    public int getBusId()
    {
        return busId;
    }

    public void setBusId(int busId)
    {
        this.busId = busId;
    }

    public String getBusNumber()
    {
        return busNumber;
    }

    public void setBusNumber(String busNumber)
    {
        this.busNumber = busNumber;
    }

    public String getBusName()
    {
        return busName;
    }

    public void setBusName(String busName)
    {
        this.busName = busName;
    }

    public int getSeatingCapacity()
    {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity)
    {
        this.seatingCapacity = seatingCapacity;
    }

    public int getStandingCapacity()
    {
        return standingCapacity;
    }

    public void setStandingCapacity(int standingCapacity)
    {
        this.standingCapacity = standingCapacity;
    }

    public Route getRoute()
    {
        return route;
    }

    public void setRoute(Route route)
    {
        this.route = route;
    }

    public String getDriverId()
    {
        return driverId;
    }

    public void setDriverId(String driverId)
    {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "ID=" + busId +
                ", Number='" + busNumber + '\'' +
                ", Seating=" + seatingCapacity +
                ", Standing=" + standingCapacity +
                ", Route='" + (route != null ? route.getRouteId() : "No Route") + '\'' +
                ", Driver='" + driverId + '\'' +
                '}';
    }
}