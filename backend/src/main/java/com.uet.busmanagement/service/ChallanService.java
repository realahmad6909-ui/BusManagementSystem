package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Challan;
import com.uet.busmanagement.repository.ChallanRepository;
import com.uet.busmanagement.util.LogManager; // Import kiya
import java.util.List;

public class ChallanService
{

    private ChallanRepository challanRepository;

    public ChallanService()
    {
        this.challanRepository = new ChallanRepository();
    }

    public String generateChallan(Challan challan)
    {
        if (challan.getAmount() <= 0)
        {
            return "Invalid Challan Amount!";
        }

        boolean isSaved = challanRepository.save(challan);

        if (isSaved)
        {
            // File Handling: Challan generation log
            LogManager.log("FINANCE_CHALLAN: Challan No " + challan.getChallanNumber() + " generated for Student ID: " + challan.getStudentId() + " | Amount: " + challan.getAmount() + " | Fine: " + challan.getFine());
            return "Challan No " + challan.getChallanNumber() + " generated successfully for Student ID: " + challan.getStudentId() + "!";
        }
        else
        {
            LogManager.log("FINANCE_ERROR: Failed to generate challan for Student ID: " + challan.getStudentId());
            return "Failed to generate challan.";
        }
    }

    public String markAsPaid(String challanNumber)
    {
        boolean isUpdated = challanRepository.updateStatus(challanNumber, "PAID");

        if (isUpdated)
        {
            // File Handling: Payment status update log
            LogManager.log("FINANCE_PAYMENT: Challan No " + challanNumber + " has been marked as PAID.");
            return "Challan " + challanNumber + " has been successfully marked as PAID!";
        }
        else
        {
            LogManager.log("FINANCE_PAYMENT_FAILED: Failed to update payment status for Challan No: " + challanNumber);
            return "Challan number not found or failed to update.";
        }
    }

    public List<Challan> getStudentChallans(String regnum)
    {
        return challanRepository.findByStudentId(regnum);
    }
    // ChallanService.java

    public boolean hasUnpaidChallan(String regnum) {

        List<Challan> challans = challanRepository.findByStudentId(regnum);
        for (Challan c : challans) {
            if ("UNPAID".equalsIgnoreCase(c.getStatus())) {
                return true;
            }
        }
        return false;
    }
}