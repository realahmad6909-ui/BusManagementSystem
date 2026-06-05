package com.uet.busmanagement.controller;

import com.uet.busmanagement.service.BookingService;
import com.uet.busmanagement.model.Student;
import com.uet.busmanagement.service.ChallanService;
import java.util.Scanner;

public class BookingController {
    private BookingService bookingService = new BookingService();
    private ChallanService challanservice = new ChallanService();
    private Scanner sc = new Scanner(System.in);

    private void bookBusFlow(String regNum) {
        System.out.print("Enter Route ID: ");
        String routeId = sc.nextLine();

        String result = bookingService.processBooking(regNum, routeId);
        System.out.println(result);

        if (result.contains("full")) {
            System.out.print("Bus is full! Do you want to register a complaint? (yes/o): ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {

                System.out.println("Complaint registered successfully for route: " + routeId);
            }
        }
    }

    private void cancelBookingFlow(String regNum) {
        String result = bookingService.cancelBooking(regNum);
        System.out.println(result);
    }

    public void showBookingMenu(Student student) {
        System.out.println("\n--- BUS BOOKING MENU ---");
        System.out.println("1. Book Bus");
        System.out.println("2. Cancel Booking");
        System.out.println("3. Back to Main Menu");
        System.out.print("Select an option: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                if (challanservice.hasUnpaidChallan(student.getRegNum())) {
                    System.out.println("ACCESS DENIED: Unpaid Challan");
                }
                bookBusFlow(student.getRegNum());
                break;
            case 2:
                cancelBookingFlow(student.getRegNum());
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid option!");
        }
    }
}