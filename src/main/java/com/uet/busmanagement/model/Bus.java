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

    public Bus(int busId, String busNumber, String busName,
               int seatingCapacity, int standingCapacity,
               Route route, String driverId) {

        this.busId = busId;
        this.busNumber = busNumber;
        this.busName = busName;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.route = route;
        this.driverId = driverId;
    }

    public int getTotalCapacity() {
        return seatingCapacity + standingCapacity;
    }

    public int getBusId()

    {
        return busId;
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "ID=" + busId +
                ", Number='" + busNumber + '\'' +
                ", Seating=" + seatingCapacity +
                ", Standing=" + standingCapacity +
                ", Route='" + route.getRouteId() + '\'' +
                ", Driver='" + driverId + '\'' +
                '}';
    }
}