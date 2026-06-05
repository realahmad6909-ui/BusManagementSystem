package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.repository.DriverRepository;
import com.uet.busmanagement.util.LogManager; // Import kiya
import java.util.List;

public class DriverService {
    private DriverRepository driverRepo = new DriverRepository();


    public void registerDriver(Driver driver) {
        // Validation: Email ya Phone check kar sakte hain
        if (driver.getName() == null || driver.getLicenseNumber() == null) {
            throw new IllegalArgumentException("Driver details are incomplete!");
        }
        driverRepo.addDriver(driver);
    }


    public void removeDriver(int id) {
        driverRepo.deleteDriver(id);
    }

    public void getDriver(int id) {

        Driver driver = driverRepo.findDriverById(id);

        if (driver != null) {
            System.out.println("--- Driver Details ---");
            System.out.println("ID: " + driver.getUserId());
            System.out.println("Name: " + driver.getName());
            System.out.println("License: " + driver.getLicenseNumber());
        } else {
            System.out.println("Error: Driver nahi mila!");
        }
    }

    public boolean status_update(int id, String status){
        return driverRepo.updateStatus(id,status);
    }


    public Bus getAssignedBus(int driverId) {

        if (driverId <= 0) {
            return null;
        }
        return driverRepo.getBusByDriverId(driverId);
    }

    public void alldriverdetails() {
        List<Driver> drivers = driverRepo.findAllDrivers(); // Repository call

        if (drivers.isEmpty()) {
            System.out.println("Koi driver record nahi mila.");
        } else {
            for (Driver d : drivers) {
                System.out.println("ID: " + d.getUserId() + " | Name: " + d.getName() + " | License: " + d.getLicenseNumber());
            }
        }
    }
}

