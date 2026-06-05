package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Complaint;
import com.uet.busmanagement.repository.ComplaintRepository;
import java.util.List;

public class ComplaintService {
    private ComplaintRepository complaintRepo = new ComplaintRepository();

    public String registerComplaint(String regNum, String routeId) {
        Complaint complaint = new Complaint(regNum, routeId);
        if (complaintRepo.saveComplaint(complaint)) {
            return "Complaint submitted successfully!";
        }
        return "Failed to submit complaint.";
    }

    public List<Complaint> getAllPendingComplaints() {
        return complaintRepo.getPendingComplaints();
    }

    public String resolveComplaintsForRoute(String routeId) {
        if (complaintRepo.markComplaintsAsResolved(routeId)) {
            return "Complaints for route " + routeId + " have been resolved.";
        } else {
            return "No pending complaints for this route.";
        }
    }
}