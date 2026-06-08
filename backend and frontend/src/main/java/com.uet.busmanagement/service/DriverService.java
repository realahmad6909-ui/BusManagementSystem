package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.repository.DriverRepository;
import com.uet.busmanagement.util.LogManager;
import java.util.List;
import java.util.Map;

public class DriverService {

    private DriverRepository driverRepo = new DriverRepository();

    public Driver getDriverByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return driverRepo.findDriverByEmail(email.trim());
    }

    public boolean recordSOSAlert(int driverId, String busNumber) {

        return driverRepo.saveSOSAlert(driverId, busNumber);
    }
    public List<Map<String, Object>> getActiveAlerts() {

        return driverRepo.getActiveAlerts();
    }

    public boolean clearEmergencyAlert(int alertId) {
        return driverRepo.resolveAlert(alertId);
    }

    public void registerDriver(Driver driver) {
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
        return driverRepo.updateStatus(id, status);
    }

    public Bus getAssignedBus(int driverId) {
        if (driverId <= 0) {
            return null;
        }
        return driverRepo.getBusByDriverId(driverId);
    }

    public List<Driver> alldriverdetails() {
        List<Driver> drivers = driverRepo.findAllDrivers();
        return drivers;
    }
}