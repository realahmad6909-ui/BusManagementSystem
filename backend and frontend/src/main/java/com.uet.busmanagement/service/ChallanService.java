package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Challan;
import com.uet.busmanagement.repository.ChallanRepository;
import com.uet.busmanagement.util.LogManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ChallanService {

    private final ChallanRepository challanRepository;

    public ChallanService() {
        this.challanRepository = new ChallanRepository();
    }

    public String generateChallan(Challan challan) {
        if (challan.getAmount() <= 0) {
            return "Invalid Challan Amount!";
        }

        boolean isSaved = challanRepository.save(challan);

        if (isSaved) {
            LogManager.log("FINANCE_CHALLAN: Challan No " + challan.getChallanNumber() +
                    " generated for Reg No: " + challan.getRegnum() +
                    " | Amount: " + challan.getAmount() + " | Fine: " + challan.getFine());
            return "Challan No " + challan.getChallanNumber() + " generated successfully!";
        } else {
            LogManager.log("FINANCE_ERROR: Failed to generate challan for Reg No: " + challan.getRegnum());
            return "Failed to generate challan.";
        }
    }

    public void generateAutomaticChallan(int studentId, String regnum) {
        Challan challan = new Challan();
        challan.setStudentId(studentId);
        challan.setRegnum(regnum); // Repository ke exact structure 'regnumb' ke liye
        challan.setAmount(5000.00); // Default transport fee
        challan.setFine(0.00);


        challan.setIssueDate(LocalDate.now().toString());
        challan.setDueDate(LocalDate.now().plusDays(15).toString());
        challan.setStatus("UNPAID");


        String uniqueChallanNo = "CHN-" + LocalDate.now().getYear() + "-" +
                UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        challan.setChallanNumber(uniqueChallanNo);


        generateChallan(challan);
    }

    public String markAsPaid(String challanNumber) {
        boolean isUpdated = challanRepository.updateStatus(challanNumber, "PAID");

        if (isUpdated) {
            LogManager.log("FINANCE_PAYMENT: Challan No " + challanNumber + " has been marked as PAID.");
            return "Challan " + challanNumber + " has been successfully marked as PAID!";
        } else {
            LogManager.log("FINANCE_PAYMENT_FAILED: Failed to update payment status for Challan No: " + challanNumber);
            return "Challan number not found or failed to update.";
        }
    }

    public List<Challan> getStudentChallans(String regnum) {
        return challanRepository.findByStudentId(regnum);
    }

    public boolean hasUnpaidChallan(String regnum) {
        List<Challan> challans = challanRepository.findByStudentId(regnum);
        if (challans != null) {
            for (Challan c : challans) {
                if ("UNPAID".equalsIgnoreCase(c.getStatus())) {
                    return true;
                }
            }
        }
        return false;
    }
}