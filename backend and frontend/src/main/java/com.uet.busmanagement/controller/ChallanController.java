//package com.uet.busmanagement.controller;
//
//import com.uet.busmanagement.model.Challan;
//import com.uet.busmanagement.service.ChallanService;
//import java.util.List;
//import java.util.Scanner;
//
//public class ChallanController {
//
//    private ChallanService challanService;
//    private static Scanner sc = new Scanner(System.in);
//
//    public ChallanController() {
//        this.challanService = new ChallanService();
//    }
//
//    public String handleGenerateChallan(String challanNumber, String regnum,int id, double amount, String dueDate, String status, String issueDate, double fine) {
//        Challan challan = new Challan();
//        challan.setChallanNumber(challanNumber);
//        challan.setRegnum(regnum);
//        challan.setStudentId(id);
//        challan.setAmount(amount);
//        challan.setDueDate(dueDate);
//        challan.setStatus(status);
//        challan.setIssueDate(issueDate);
//        challan.setFine(fine);
//
//        return challanService.generateChallan(challan);
//    }
//
//    public String handleMarkAsPaid(String challanNumber) {
//        return challanService.markAsPaid(challanNumber);
//    }
//
//    public List<Challan> handleGetStudentChallans(String regnum) {
//        return challanService.getStudentChallans(regnum);
//    }
//
//    public void manageChallans() {
//        System.out.println("1. Generate Challan | 2. Mark Paid Challans  ");
//        int ch = sc.nextInt();
//        if (ch == 1) {
//            System.out.println("Challan Number,Reg Number,Student id,Amount,Due Date,Status,ISSUE DATE,Fine");
//            handleGenerateChallan(sc.next(),sc.next(),sc.nextInt(),sc.nextInt(),sc.next(),sc.next(),sc.next(),sc.nextInt());
//        }
//        if (ch==2) {
//            System.out.println("Challan Number");
//            handleMarkAsPaid(sc.next());
//        }
//
//    }
//}

package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Challan;
import com.uet.busmanagement.service.ChallanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/finance")
public class ChallanController {

    private final ChallanService challanService = new ChallanService();

    // 1. View Finance Dashboard / All Student Challans
    @GetMapping("/dashboard")
    public String financeDashboard(@RequestParam(value = "searchReg", required = false) String searchReg, Model model) {
        if (searchReg != null && !searchReg.trim().isEmpty()) {
            List<Challan> studentChallans = challanService.getStudentChallans(searchReg);
            model.addAttribute("challans", studentChallans);
            model.addAttribute("searchedReg", searchReg);

            // Check unpaid challan using your service method
            boolean hasUnpaid = challanService.hasUnpaidChallan(searchReg);
            model.addAttribute("hasUnpaidAlert", hasUnpaid);
        }
        return "manage-challans";
    }

    @PostMapping("/generate")
    public String createChallan(@RequestParam("regnumb") String regnumb,
                                @RequestParam("studentId") int studentId,
                                @RequestParam("amount") double amount,
                                @RequestParam("dueDate") String dueDate,
                                @RequestParam("fine") double fine) {

        Challan challan = new Challan();

        String uniqueChallanNum = "CHN-" + LocalDate.now().getYear() + "-" + (int) (Math.random() * 90000 + 10000);

        challan.setChallanNumber(uniqueChallanNum);
        challan.setStudentId(studentId);

        challan.setRegnum(regnumb);

        challan.setAmount(amount);
        challan.setDueDate(dueDate);
        challan.setIssueDate(LocalDate.now().toString());
        challan.setFine(fine);
        challan.setStatus("UNPAID"); // Database ENUM valid value

        String resultMessage = challanService.generateChallan(challan);
        return "redirect:/finance/dashboard?searchReg=" + regnumb;
    }

    // 🌟 Purane dono methods (payChallan aur markAsPaid) ko hata kar bas yeh ek method rakhein:
    @GetMapping("/pay")
    public String markAsPaid(@RequestParam("challanNumber") String challanNumber,
                             @RequestParam("searchReg") String searchReg) {

        // 1. Aapki service ka jo bhi sahi method naam hai, use call karke status PAID karein
        challanService.markAsPaid(challanNumber);

        // 2. Wapas dashboard par redirect ho jayein search query parameter ke sath
        return "redirect:/finance/dashboard?searchReg=" + searchReg;
    }
}