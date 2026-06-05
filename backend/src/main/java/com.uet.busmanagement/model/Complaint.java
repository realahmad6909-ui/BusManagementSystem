package com.uet.busmanagement.model;

public class Complaint {
    private int complaintId;
    private String regnum;
    private String routeId;
    private String status; // 'PENDING' or 'RESOLVED'

    public Complaint(String studentReg, String routeId) {
        this.regnum = studentReg;
        this.routeId = routeId;
        this.status = "PENDING";
    }

    // Getters and Setters
    public String getRegnum() { return regnum; }
    public String getRouteId() { return routeId; }
    public String getStatus() { return status; }
}