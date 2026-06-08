//package com.uet.busmanagement.controller;
//
//import com.uet.busmanagement.model.Complaint;
//import com.uet.busmanagement.service.ComplaintService;
//import java.util.List;
//import java.util.Scanner;
//
//public class ComplaintController {
//    private ComplaintService complaintService = new ComplaintService();
//    private Scanner sc = new Scanner(System.in);
//
//    // Student Flow: Complaint Register karne ke liye
//    public void registerComplaint(String regNum, String routeId) {
//        System.out.println(complaintService.registerComplaint(regNum, routeId));
//    }
//
//    public void showAdminComplaintMenu() {
//        while (true) {
//            System.out.println("\n--- COMPLAINT MANAGEMENT MENU ---");
//            System.out.println("1. View All Pending Complaints");
//            System.out.println("2. Resolve Complaints for a Route");
//            System.out.println("3. Back to Main Menu");
//            System.out.print("Select an option: ");
//
//            int choice = sc.nextInt();
//            sc.nextLine(); // Buffer clear
//
//            switch (choice) {
//                case 1:
//                    displayPendingComplaints();
//                    break;
//                case 2:
//                    System.out.print("Enter Route ID to resolve: ");
//                    String routeId = sc.nextLine();
//                    System.out.println(complaintService.resolveComplaintsForRoute(routeId));
//                    break;
//                case 3:
//                    return; // Wapis main menu par
//                default:
//                    System.out.println("Invalid option!");
//            }
//        }
//    }
//
//    private void displayPendingComplaints() {
//        List<Complaint> pending = complaintService.getAllPendingComplaints();
//        if (pending == null || pending.isEmpty()) {
//            System.out.println("No pending complaints found.");
//        } else {
//            System.out.println("\n--- LIST OF PENDING COMPLAINTS ---");
//            for (Complaint c : pending) {
//                System.out.println("Route ID: " + c.getRouteId() + " | By: " + c.getRegnum());
//            }
//        }
//    }
//}

package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Complaint;
import com.uet.busmanagement.service.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService = new ComplaintService();

    // 1. ADMIN DASHBOARD: View All Pending Complaints
    @GetMapping("/admin/view")
    public String viewAdminComplaints(Model model) {
        List<Complaint> pendingList = complaintService.getAllPendingComplaints();
        model.addAttribute("complaints", pendingList);
        return "manage-complaints";
    }

    // 2. STUDENT ACTION: Submit Complaint
    @PostMapping("/submit")
    public String studentSubmitComplaint(@RequestParam("regNum") String regNum,
                                         @RequestParam("routeId") String routeId,
                                         Model model) {
        String statusMessage = complaintService.registerComplaint(regNum, routeId);
        model.addAttribute("message", statusMessage);
        // Direct back to standard student view or dashboard mapping
        return "redirect:/student/dashboard?msg=" + statusMessage;
    }

    // 3. ADMIN ACTION: Resolve Route Complaints Group
    @GetMapping("/admin/resolve")
    public String resolveRouteComplaints(@RequestParam("routeId") String routeId) {
        complaintService.resolveComplaintsForRoute(routeId);
        return "redirect:/complaints/admin/view";
    }
}