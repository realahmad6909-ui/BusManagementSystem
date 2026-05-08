package com.uet.busmanagement.controller;
import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.model.Bus;
import java.util.ArrayList;

public class BusController {

    private ArrayList<Bus> buses;

    public BusController() {
        buses = new ArrayList<>();
    }

    public void addBus(Bus bus) {
        buses.add(bus);
        System.out.println("Bus added successfully!");
    }

    public void displayAllBuses() {
        for (Bus b : buses) {
            System.out.println(b);  // automatic toString call
        }
    }

    public void createBus(Route route) {

        Bus b1 = new Bus(1, "2894", "UET Bus",
                40, 20, route, "D1");

        addBus(b1);
        route.addBus(b1);

    }
}
