package com.uet.busmanagement.model;

public class Route {

    private String routeId;   // e.g., "R1"
    private String routeName; // e.g., "Main Campus to Hostel"

    public Route() {
    }

    public Route(String routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }


    public String getRouteId() { return routeId; }
    public String getRouteName() { return routeName; }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}