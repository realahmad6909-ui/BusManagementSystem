package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Bus;

public class BusController {

    public void createBus() {

        Bus bus = new Bus(
                1,
                "BUS-01",
                "UET BUS",
                40,
                "R1",
                "D1"
        );

        System.out.println("Bus Created Successfully!");
        System.out.println("Bus Name: " + bus.getBusName());
        System.out.println("Available Seats: " + bus.getAvailableSeats());
    }
}