package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Challan;
import com.uet.busmanagement.repository.ChallanRepository;
import java.util.List;

public class ChallanService
{

    private ChallanRepository ChallanRepository;

    public ChallanService()
    {
        this.ChallanRepository = new ChallanRepository();
    }

    // 1. Naya Challan generate karne ki logic
    public String generateChallan(Challan challan)
    {
        boolean isSaved = ChallanRepository.save(challan);
        if (isSaved)
        {
            return "Challan " + challan.getChallanNumber() + " generated successfully!";
        }
        else
        {
            return "Failed to generate challan.";
        }
    }

    // 2. Challan status update karne ki logic (e.g., Bank se fee receive hone par PAID karna)
    public String markAsPaid(String challanNumber)
    {
        boolean isUpdated = ChallanRepository.updateStatus(challanNumber, "PAID");
        if (isUpdated)
        {
            return "Challan status updated to PAID successfully!";
        }
        else
        {
            return "Failed to update challan status. Please check challan number.";
        }
    }

    // 3. Kisi specific student ke saare challans ki history dekhne ke liye
    public List<Challan> getStudentChallans(int studentId)
    {
        return ChallanRepository.findByStudentId(studentId);
    }
}