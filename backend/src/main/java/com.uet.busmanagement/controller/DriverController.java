package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.service.DriverService;

import java.sql.SQLOutput;
import java.util.Scanner;

public class DriverController {
    private DriverService driverService;
    private static Scanner sc = new Scanner(System.in);

    public DriverController() {
        this.driverService = new DriverService();
    }

    public String handleAddDriver(String name, String email, String password, String phone, String license) {
        if (name.trim().isEmpty() || email.trim().isEmpty() || license.trim().isEmpty()) {
            return "Name, Email, and License are required!";
        }

        Driver newDriver = new Driver( name, email, password, "DRIVER", phone, license);

        driverService.registerDriver(newDriver);

        return "Driver " + name + " registered successfully!";
    }

    public String handleRemoveDriver(String id) {
        try {
            int driverId = Integer.parseInt(id);
            driverService.removeDriver(driverId);
            return "Driver with ID " + id + " removed.";
        } catch (NumberFormatException e) {
            return "Invalid ID format. Please provide a numeric ID.";
        }
    }

    public void handledriverdetails(){
        driverService.alldriverdetails();
    }
    public void handlefinddriver(int id) {
        driverService.getDriver(id);
    }

    public void handleupdatestatus(int id, String status){
        driverService.status_update(id,status);
    }

    public void manageDrivers() {
        System.out.println("1. Add Driver | 2. Delete Driver | 3. Find Driver | 4. Display All Drivers | 5. Update Driver Status ");
        int ch = sc.nextInt();
        if (ch ==1){
            System.out.println("Name, E-Mail,Password,Phone,Liscence");
            handleAddDriver(sc.next(),sc.next(),sc.next(),sc.next(),sc.next());
        }
        if (ch ==2) {
            System.out.println("Driver-id");
            handleRemoveDriver(sc.next());
        }
        if (ch ==3) {
            System.out.println("Driver-id");
            handlefinddriver(sc.nextInt());
        }
        if (ch ==4) {
            handledriverdetails();
        }
        if (ch ==5) {
            System.out.println("Driver-id,Status");
            handleupdatestatus(sc.nextInt(),sc.next());
        }

    }
    public void showDriverMenu() {
        int choice = 0;
        while (choice != 4) {
            System.out.println("\n--- DRIVER DASHBOARD ---");
            System.out.println("1. View Assigned Bus");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter driver id: ");
                    driverService.getAssignedBus(sc.nextInt()); break;

                case 4: System.out.println("Logging out..."); break;
                default: System.out.println("Invalid choice!");
            }
        }
    }
}