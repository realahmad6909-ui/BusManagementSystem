//package com.uet.busmanagement;
//
//
//import com.uet.busmanagement.controller.AuthController;
//import com.uet.busmanagement.controller.BusController;
//import com.uet.busmanagement.controller.BookingController;
//import com.uet.busmanagement.controller.ChallanController;
//import com.uet.busmanagement.controller.StudentController;
//import com.uet.busmanagement.controller.StopsController;
//import com.uet.busmanagement.controller.DriverController;
//import com.uet.busmanagement.controller.RouteController;
//import com.uet.busmanagement.model.User;
//
//import java.util.Scanner;
//
//public class Main {
//    private static Scanner sc = new Scanner(System.in);
//
//    // Controllers Initialize
//    private static AuthController authCtrl = new AuthController();
//    private static BusController busCtrl = new BusController();
//    private static RouteController routeCtrl = new RouteController();
//    private static StopsController stopCtrl = new StopsController();
//    private static StudentController stuCtrl = new StudentController();
//    private static ChallanController challanCtrl = new ChallanController();
//    private static DriverController driverCtrl = new DriverController();
//
//    public static void main(String[] args) {
//        while (true) {
//            System.out.println("\n--- UET BUS MANAGEMENT SYSTEM ---");
//            System.out.println("1. Admin Dashboard");
//            System.out.println("2. Student Dashboard");
//            System.out.println("3. Driver Dashboard");
//            System.out.println("4. View All Routes");
//            System.out.println("5. Exit");
//            System.out.print("Select Option: ");
//            int choice = sc.nextInt();
//            sc.nextLine();
//
//            switch (choice) {
//                case 1:
//                    authCtrl.loginFlow("ADMIN");
//                    break;
//                case 2:
//                    stuCtrl.studentFlow(); break;
//                case 3: authCtrl.loginFlow("DRIVER");; break;
//                case 4:
//                    routeCtrl.displayallroutes();
//                    System.out.print("Enter Route ID to view Stops (Enter 0 to Exit)");
//                    String rId = sc.next();if (!rId.equals("0")) {
//                        stopCtrl.handleViewStops(rId);
//                    }break;
//                case 5:
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid Option!");
//            }
//        }
//    }
