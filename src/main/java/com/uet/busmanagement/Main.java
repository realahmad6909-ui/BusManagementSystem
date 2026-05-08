package com.uet.busmanagement;

import com.uet.busmanagement.controller.BusController;
import com.uet.busmanagement.controller.RouteController;
import com.uet.busmanagement.controller.StudentController;
import com.uet.busmanagement.controller.BookingController;
import com.uet.busmanagement.model.Student;
import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.model.Booking;

public class Main {

    public static void main(String[] args) {

        RouteController routeController = new RouteController();

        Route r1 = new Route("R1", "UET", "Nishtar Town");
        r1.addStop("Mughalpura");
        r1.addStop("Naseerabad");
        r1.addStop("Qainchi");
        r1.addStop("Ghazi Road");
        r1.addStop("Chungi");
        r1.addStop("Kamahan");
        routeController.addRoute(r1);


        BusController busController = new BusController();
        busController.createBus(r1);
        System.out.println(r1.displayBuses());
        System.out.println(routeController.displayAllRoutes());
        StudentController sc = new StudentController();

        Student s1 = new Student(1, "Ali", "CYS", "2025-CYS-31");
        Student s2 = new Student(2, "Ahmed", "CYS", "2025-CYS-35");

        sc.addStudent(s1);
        sc.addStudent(s2);

        sc.assignBus(1, "2894");
        sc.assignBus(2, "2894");

        sc.displayStudents();
        BookingController bc = new BookingController();

        bc.createBooking(1, 1, "2894");
        bc.createBooking(2, 2, "2894");

        bc.showBookings();
    }
}



