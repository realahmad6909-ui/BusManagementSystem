package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Challan;
import com.uet.busmanagement.service.ChallanService;
import java.util.List;
import java.util.Scanner;

public class ChallanController {

    private ChallanService challanService;
    private static Scanner sc = new Scanner(System.in);

    public ChallanController() {
        this.challanService = new ChallanService();
    }

    public String handleGenerateChallan(String challanNumber, String regnum,int id, double amount, String dueDate, String status, String issueDate, double fine) {
        Challan challan = new Challan();
        challan.setChallanNumber(challanNumber);
        challan.setRegnum(regnum);
        challan.setStudentId(id);
        challan.setAmount(amount);
        challan.setDueDate(dueDate);
        challan.setStatus(status);
        challan.setIssueDate(issueDate);
        challan.setFine(fine);

        return challanService.generateChallan(challan);
    }

    public String handleMarkAsPaid(String challanNumber) {
        return challanService.markAsPaid(challanNumber);
    }

    public List<Challan> handleGetStudentChallans(String regnum) {
        return challanService.getStudentChallans(regnum);
    }

    public void manageChallans() {
        System.out.println("1. Generate Challan | 2. Mark Paid Challans  ");
        int ch = sc.nextInt();
        if (ch == 1) {
            System.out.println("Challan Number,Reg Number,Student id,Amount,Due Date,Status,ISSUE DATE,Fine");
            handleGenerateChallan(sc.next(),sc.next(),sc.nextInt(),sc.nextInt(),sc.next(),sc.next(),sc.next(),sc.nextInt());
        }
        if (ch==2) {
            System.out.println("Challan Number");
            handleMarkAsPaid(sc.next());
        }

    }
}