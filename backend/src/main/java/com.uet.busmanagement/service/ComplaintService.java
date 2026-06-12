package com.uet.busmanagement.service;

import com.uet.busmanagement.model.Complaint;
import com.uet.busmanagement.repository.ComplaintRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepo = new ComplaintRepository();

    public String registerComplaint(String regNum, String routeId) {
        Complaint complaint = new Complaint(regNum, routeId);

        complaint.setStatus("PENDING");

        if (complaintRepo.saveComplaint(complaint)) {
            return "Success";
        }
        return "Failed";
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