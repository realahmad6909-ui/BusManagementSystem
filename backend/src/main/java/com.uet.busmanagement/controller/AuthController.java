package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.User;
import com.uet.busmanagement.service.AuthService;
import com.uet.busmanagement.service.StudentService;
import com.uet.busmanagement.controller.RouteController;

import java.util.Scanner;

public class AuthController {

    private AuthService authService;
    private static AuthController authCtrl = new AuthController();
    private static BusController busCtrl = new BusController();
    private static RouteController routeCtrl = new RouteController();
    private static StopsController stopCtrl = new StopsController();
    private static StudentController stuCtrl = new StudentController();
    private static ChallanController challanCtrl = new ChallanController();
    private static DriverController driverCtrl = new DriverController();
    private static ComplaintController complCtrl = new ComplaintController();
    private static Scanner sc = new Scanner(System.in);

    public AuthController() {
        this.authService = new AuthService();
    }

    public String handleRegistration(int userId, String name, String email, String password, String role, String phone, String profilePicPath) {
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setPhone(phone);
        newUser.setProfilePicPath(profilePicPath);

        return authService.registerUser(newUser);
    }

    public User handleLogin(String email, String password) {

        return authService.handleLogin(email, password);
    }

    private StudentService studentService = new StudentService(); // Admin functions ke liye

    public void handleAdminDashboard() {


        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("Welcome to Admin Dashboard");
                    System.out.println("1. Manage Students | 2. Manage Buses | 3. Manage Routes 4.| Manage Drivers | 5. Manage Challans | 6. View / Resolve Complaints | 7. Logout");
            int choice = sc.nextInt();

            switch (choice) {
                 case 1: stuCtrl.manageStudents(); break;
                 case 2: busCtrl.manageBuses(); break;
                 case 3: routeCtrl.manageRoutes(); break;
                 case 4: driverCtrl.manageDrivers();break;
                 case 5: challanCtrl.manageChallans();break;
                 case 6 :
                     complCtrl.showAdminComplaintMenu();break;
                 case 7:
                    exit = true;
                    break;
            }
        }

    }
    public static void loginFlow(String role) {
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Pass: "); String pass = sc.nextLine();
        User user = authCtrl.handleLogin(email, pass);

        if (user != null && user.getRole().equalsIgnoreCase(role)) {
            if (role.equals("ADMIN")) authCtrl.handleAdminDashboard();
            else if (role.equals("DRIVER")) {
                driverCtrl.showDriverMenu();
            };
        } else {
            System.out.println("Access Denied!");
        }
    }

}