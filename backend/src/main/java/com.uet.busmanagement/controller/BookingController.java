//package com.uet.busmanagement.controller;
//
//import com.uet.busmanagement.service.BookingService;
//import com.uet.busmanagement.model.Student;
//import com.uet.busmanagement.service.ChallanService;
//import java.util.Scanner;
//
//public class BookingController {
//    private BookingService bookingService = new BookingService();
//    private ChallanService challanservice = new ChallanService();
//    private Scanner sc = new Scanner(System.in);
//
//    private void bookBusFlow(String regNum) {
//        System.out.print("Enter Route ID: ");
//        String routeId = sc.nextLine();
//
//        String result = bookingService.processBooking(regNum, routeId);
//        System.out.println(result);
//
//        if (result.contains("full")) {
//            System.out.print("Bus is full! Do you want to register a complaint? (yes/o): ");
//            if (sc.nextLine().equalsIgnoreCase("yes")) {
//
//                System.out.println("Complaint registered successfully for route: " + routeId);
//            }
//        }
//    }
//
//    private void cancelBookingFlow(String regNum) {
//        String result = bookingService.cancelBooking(regNum);
//        System.out.println(result);
//    }
//
//    public void showBookingMenu(Student student) {
//        System.out.println("\n--- BUS BOOKING MENU ---");
//        System.out.println("1. Book Bus");
//        System.out.println("2. Cancel Booking");
//        System.out.println("3. Back to Main Menu");
//        System.out.print("Select an option: ");
//
//        int choice = sc.nextInt();
//        sc.nextLine();
//
//        switch (choice) {
//            case 1:
//                if (challanservice.hasUnpaidChallan(student.getRegNum())) {
//                    System.out.println("ACCESS DENIED: Unpaid Challan");
//                }
//                bookBusFlow(student.getRegNum());
//                break;
//            case 2:
//                cancelBookingFlow(student.getRegNum());
//                break;
//            case 3:
//                return;
//            default:
//                System.out.println("Invalid option!");
//        }
//    }
//}

package com.uet.busmanagement.controller;

import com.uet.busmanagement.service.BookingService;
import com.uet.busmanagement.service.ChallanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    // Spring khud in services ko manage aur inject karega (No 'new' keyword)
    private final BookingService bookingService;
    private final ChallanService challanService;

    @Autowired
    public BookingController(BookingService bookingService, ChallanService challanService) {
        this.bookingService = bookingService;
        this.challanService = challanService;
    }

    // --- 1. BUS BOOK KARNE KE LIYE (POST REQUEST) ---
    @PostMapping("/book")
    public String handleBookBus(@RequestParam String regNum, @RequestParam int routeId) {

        // Rule: Agar student ka koi challan unpaid hai to booking nahi karne deni
        if (challanService.hasUnpaidChallan(regNum)) {
            return "ACCESS DENIED: Unpaid Challan majood hai. Pehle fee jama karwayein.";
        }

        // String.valueOf agar service abhi bhi string leti hai, warna service mein bhi int/long karein
        return bookingService.processBooking(regNum, String.valueOf(routeId));
    }

    // --- 2. BOOKING CANCEL KARNE KE LIYE (DELETE REQUEST) ---
    @DeleteMapping("/cancel")
    public String handleCancelBooking(@RequestParam String regNum) {
        return bookingService.cancelBooking(regNum);
    }
}