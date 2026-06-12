//package com.uet.busmanagement.controller;
//
//import com.uet.busmanagement.model.Route;
//import com.uet.busmanagement.service.RouteService;
//import com.uet.busmanagement.controller.StopsController;
//
//import java.util.Scanner;
//
//public class RouteController {
//    private RouteService routeService;
//    private static Scanner sc = new Scanner(System.in);
//    private static StopsController stopctrl = new StopsController();
//
//    public RouteController() {
//        this.routeService = new RouteService();
//    }
//
//    public void handleAddRoute(String id, String name) {
//        if (id.trim().isEmpty() || name.trim().isEmpty()) {
//            System.out.println("Route ID and Name cannot be empty!");
//        }
//        routeService.addRoute(id, name);
//    }
//
//    public void displayallroutes() {
//        routeService.displayAllRoutes();
//    }
//
//    public void deleteroute(String id) {
//        routeService.removeRoute(id);
//    }
//
//    public void handleShowRouteDetails(String routeId) {
//        new StopsController().handleViewStops(routeId);
//    }
//
//    public void manageRoutes() {
//        System.out.println("1. Add Route | 2. Delete Route | 3. Display All Routes | 4. Add Stops ");
//        int ch = sc.nextInt();
//        if (ch == 1) {
//            System.out.println("Route-id,Route Name");
//            handleAddRoute(sc.next(),sc.next());
//        }
//        if (ch == 3) {
//            displayallroutes();
//        }
//        if (ch == 2) {
//            System.out.println("Route-ID");
//            deleteroute(sc.next());
//        }
//        if (ch == 4) {
//            System.out.println("Route ID, NAME, ARRIVAL MORNING TIME, AFTERNOON DEPARTURE TIME");
//            stopctrl.handleAddStop(sc.next(),sc.next(),sc.next(),sc.next());
//        }
//    }
//}
///


package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.model.Stops;
import com.uet.busmanagement.service.RouteService;
import com.uet.busmanagement.service.StopsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/routes")
public class RouteController {


    private final RouteService routeService = new RouteService();
    private final StopsService stopsService = new StopsService();


    @GetMapping("/manage")
    public String manageRoutes(Model model) {
        // Model se direct saare routes load kiye
        List<Route> routes = routeService.displayAllRoutes();
        model.addAttribute("routes", routes);

        // Frontend par layout block ab clean load hoga (Stops popup se handle honge)
        return "manage-routes";
    }

    @PostMapping("/delete-map")
    public String deleteRouteMap(@RequestParam("routeId") String routeId,
                                 RedirectAttributes redirectAttributes) {

        boolean updated = routeService.updateRouteMap(routeId, "");

        if (updated) {
            redirectAttributes.addFlashAttribute("success", "Map link deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete map link.");
        }

        return "redirect:/routes/manage";
    }
    @PostMapping("/update-map")
    public String updateRouteMap(@RequestParam("routeId") String routeId,
                                 @RequestParam("mapUrl") String mapUrl,
                                 RedirectAttributes redirectAttributes) {

        boolean updated = routeService.updateRouteMap(routeId, mapUrl);

        if (updated) {
            redirectAttributes.addFlashAttribute("success", "Map URL updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update map URL.");
        }

        return "redirect:/routes/manage";
    }


    @GetMapping("/api/stops")
    @ResponseBody
    public List<Stops> getStopsForModal(@RequestParam("routeId") String routeId) {
        // Direct repo hatakar aapki StopsService ka updated method call kiya
        return stopsService.getStopsByRouteId(routeId);
    }


    @PostMapping("/add")
    public String handleAddRoute(@RequestParam("routeId") String id,
                                 @RequestParam("routeName") String name,
                                 @RequestParam("mapUrl") String url)
    {
        routeService.addRoute(id, name,url);
        return "redirect:/routes/manage";
    }

    @PostMapping("/add-stop")
    public String handleAddStopToRoute(@RequestParam("routeId") String routeId,
                                       @RequestParam("stopName") String stopName,
                                       @RequestParam("morningTime") String morningTime,
                                       @RequestParam("afternoonTime") String afternoonTime) {


        stopsService.addStopToRoute(routeId, stopName, morningTime, afternoonTime);
        return "redirect:/routes/manage";
    }


    @GetMapping("/delete")
    public String deleteRoute(@RequestParam("routeId") String id) {
        routeService.removeRoute(id);
        return "redirect:/routes/manage";
    }
}