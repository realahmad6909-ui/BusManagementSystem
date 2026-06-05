package com.uet.busmanagement.model;

public class Bus {
    private int busId;
    private String busNumber;
    private String busName;
    private int seatingCapacity;
    private int standingCapacity;
    private Route route;
    private Driver driver;
    private String status;

    public Bus() {
    }

    public Bus(String status, Driver driver, Route route, int standingCapacity, int seatingCapacity, String busName, String busNumber, int busId) {
        this.status = status;
        this.driver = driver;
        this.route = route;
        this.standingCapacity = standingCapacity;
        this.seatingCapacity = seatingCapacity;
        this.busName = busName;
        this.busNumber = busNumber;
        this.busId = busId;
    }

    // Getters and Setters
    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public int getStandingCapacity() {
        return standingCapacity;
    }

    public void setStandingCapacity(int standingCapacity) {
        this.standingCapacity = standingCapacity;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busId=" + busId +
                ", busNumber='" + busNumber + '\'' +
                ", busName='" + busName + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                ", standingCapacity=" + standingCapacity +
                ", route=" + route +
                ", driver=" + driver +
                ", status='" + status + '\'' +
                '}';
    }
}




