package com.uet.busmanagement;

import com.uet.busmanagement.controller.BusController;
import com.uet.busmanagement.controller.RouteController;
import com.uet.busmanagement.model.Route;

public class Main {

    public static void main(String[] args) {

        BusController controller = new BusController();

        controller.createBus();
        System.out.println("=========== ROUTES ===========");


        RouteController routeController = new RouteController();


        Route r1 = new Route("R1", "UET", "Nishtar Town");
        r1.addStop("Mughalpura");
        r1.addStop("Naseerabad");
        r1.addStop("Qainchi");
        r1.addStop("Ghazi Road");
        r1.addStop("Chungi");
        r1.addStop("Kamahan");
        r1.addStop("Nishtar Town");

        routeController.addRoute(r1);

        routeController.displayAllRoutes();
    }
}



