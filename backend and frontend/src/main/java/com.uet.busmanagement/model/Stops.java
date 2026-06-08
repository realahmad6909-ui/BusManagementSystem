package com.uet.busmanagement.model;

public class Stops {

    private int stop_id;
    private String route_id;      // Foreign Key
    private String stop_name;
    private String morning_arrival_time;  // e.g., "08:00 AM"
    private String afternoon_departure_time; // e.g., "08:15 AM"

    public Stops() {
    }

    public Stops(int stop_id, String route_id, String stop_name, String morning_arrival_time, String afternoon_departure_time) {
        this.stop_id = stop_id;
        this.route_id = route_id;
        this.stop_name = stop_name;
        this.morning_arrival_time = morning_arrival_time;
        this.afternoon_departure_time = afternoon_departure_time;
    }

    // Getters

    public int getStop_id() {
        return stop_id;
    }

    public String getStop_name() { return stop_name; }
    public String getMorning_arrival_time() { return morning_arrival_time; }
    public String getAfternoon_departure_time() { return afternoon_departure_time; }

    public void setStop_id(int stop_id) {
        this.stop_id = stop_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public void setMorning_arrival_time(String morning_arrival_time) {
        this.morning_arrival_time = morning_arrival_time;
    }

    public void setAfternoon_departure_time(String afternoon_departure_time) {
        this.afternoon_departure_time = afternoon_departure_time;
    }
}