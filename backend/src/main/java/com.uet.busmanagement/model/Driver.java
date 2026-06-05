package com.uet.busmanagement.model;

public class Driver extends User{
        private String licenseNumber;
        private String status;


        public Driver() {
            super(); // Parent User class ka constructor call
        }

        // Parametrized Constructor
        public Driver(String name, String email, String password,
                      String role, String phone, String licenseNumber) {

            super();
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
            this.phone = phone;
            this.licenseNumber = licenseNumber;
        }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLicenseNumber() {
            return licenseNumber;
        }

        public void setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
        }
    }