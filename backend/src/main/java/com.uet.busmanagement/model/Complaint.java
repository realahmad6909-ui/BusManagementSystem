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

    public String getRegnum() { return regnum; }
    public String getRouteId() { return routeId; }
    public String getStatus() { return status; }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}