package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Complaint;
import com.uet.busmanagement.service.ComplaintService;
import java.util.List;
import java.util.Scanner;

public class ComplaintController {
    private ComplaintService complaintService = new ComplaintService();
    private Scanner sc = new Scanner(System.in);

    // Student Flow: Complaint Register karne ke liye
    public void registerComplaint(String regNum, String routeId) {
        System.out.println(complaintService.registerComplaint(regNum, routeId));
    }

    public void showAdminComplaintMenu() {
        while (true) {
            System.out.println("\n--- COMPLAINT MANAGEMENT MENU ---");
            System.out.println("1. View All Pending Complaints");
            System.out.println("2. Resolve Complaints for a Route");
            System.out.println("3. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Buffer clear

            switch (choice) {
                case 1:
                    displayPendingComplaints();
                    break;
                case 2:
                    System.out.print("Enter Route ID to resolve: ");
                    String routeId = sc.nextLine();
                    System.out.println(complaintService.resolveComplaintsForRoute(routeId));
                    break;
                case 3:
                    return; // Wapis main menu par
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void displayPendingComplaints() {
        List<Complaint> pending = complaintService.getAllPendingComplaints();
        if (pending == null || pending.isEmpty()) {
            System.out.println("No pending complaints found.");
        } else {
            System.out.println("\n--- LIST OF PENDING COMPLAINTS ---");
            for (Complaint c : pending) {
                System.out.println("Route ID: " + c.getRouteId() + " | By: " + c.getRegnum());
            }
        }
    }
}