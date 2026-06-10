//package com.uet.busmanagement.controller;
//
//import com.uet.busmanagement.config.db;
//import com.uet.busmanagement.model.User;
//import com.uet.busmanagement.service.AuthService;
//import com.uet.busmanagement.service.DriverService;
//import com.uet.busmanagement.service.RouteService;
//import com.uet.busmanagement.service.StudentService;
//import jakarta.servlet.http.HttpSession; // Session security ke liye
//import org.springframework.beans.factory.annotation.Autowired; // For injection
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Connection;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class ViewController {
//
//
//    private final AuthService authService;
//
//    @Autowired
//    public ViewController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @GetMapping("/")
//    public String showWelcomePage() {
//        return "index";
//    }
//
//    @GetMapping("/dashboards")
//    public String showDashboardsPage() {
//        return "dashboards";
//    }
//
//
//    @GetMapping("/admin/login")
//    public String showAdminLoginPage(Model model) {
//        model.addAttribute("userForm", new User());
//        return "admin-login";
//    }
//
//
//    @PostMapping("/admin/submit-login")
//    public String handleAdminLogin(@ModelAttribute("userForm") User userForm,
//                                   HttpSession session,
//                                   Model model) {
//
//        User authenticatedUser = authService.handleLogin(userForm.getEmail(), userForm.getPassword());
//
//        if (authenticatedUser != null && authService.isAdminAuthorized(authenticatedUser)) {
//
//            session.setAttribute("loggedInAdmin", authenticatedUser);
//
//            return "redirect:/admin/dashboard";
//        }
//
//        model.addAttribute("error", "Invalid Admin Email or Password!");
//        return "admin-login";
//    }
//
//
//    @GetMapping("/admin/dashboard")
//    public String showAdminDashboard(HttpSession session, Model model) {
//        // Security check
//        if (session.getAttribute("loggedInAdmin") == null) {
//            return "redirect:/admin/login?error=Please login first!";
//        }
//        RouteService routeService= new RouteService();
//        List<String> dbRoutes = routeService.getAllRouteIdsForDropdown();
//
//        model.addAttribute("availableRoutes", dbRoutes);
//
//        return "admin-dashboard";
//    }
//
//    @GetMapping("/admin/logout")
//    public String adminLogout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/admin/login?msg=Logged out successfully";
//    }
//    @GetMapping("/admin/api/alerts")
//    @ResponseBody
//    public List<Map<String, Object>> fetchLiveAlerts() {
//        System.out.println(" [Live Check]: Admin dashboard background polling hit!");
//        DriverService driverService= new DriverService();
//        return driverService.getActiveAlerts();
//    }
//
//
//        StudentService studentService = new StudentService();
//    @GetMapping("/student/api/get-notice")
//    @ResponseBody
//    public String getStudentNotice() {
//        // Bina kisi route ke jhanjhat ke direct "ALL" ya latest notice uthao
//        String notice = studentService.getNoticeForStudentDashboard("ALL");
//
//        if (notice == null || notice.trim().isEmpty()) {
//            return "NO_NEW_NOTICE";
//        }
//        return notice.trim();
//    }
//
//
//    @PostMapping("/admin/api/alerts/resolve")
//    @ResponseBody
//    public String resolveLiveAlert(@RequestParam("id") int id) {
//        DriverService driverService = new DriverService();
//        boolean success = driverService.clearEmergencyAlert(id);
//        return success ? "SUCCESS" : "ERROR";
//    }
//    @PostMapping("/admin/api/send-notice")
//    @ResponseBody
//    public String sendNoticeBroadcast(@RequestParam("route") String route, @RequestParam("message") String message) {
//        if (message == null || message.trim().isEmpty()) {
//            return "ERROR: Message is empty";
//        }
//
//        boolean success = studentService.saveAdminNotice(route.trim(), message.trim());
//
//        if (success) {
//            System.out.println(" Notice successfully saved in DB: " + message);
//            return "SUCCESS";
//        } else {
//            System.out.println("Failed to save notice in Database!");
//            return "ERROR: Database insertion failed";
//        }
//    }
//}
//

package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.User;
import com.uet.busmanagement.service.AuthService;
import com.uet.busmanagement.service.DriverService;
import com.uet.busmanagement.service.RouteService;
import com.uet.busmanagement.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ViewController {

    private final AuthService authService;
    private final StudentService studentService;
    private final DriverService driverService;
    private final RouteService routeService;

    @Autowired
    public ViewController(AuthService authService, StudentService studentService,
                          DriverService driverService, RouteService routeService) {
        this.authService = authService;
        this.studentService = studentService;
        this.driverService = driverService;
        this.routeService = routeService;
    }

    @GetMapping("/")
    public String showWelcomePage() {
        return "index";
    }

    @GetMapping("/dashboards")
    public String showDashboardsPage() {
        return "dashboards";
    }

    @GetMapping("/admin/login")
    public String showAdminLoginPage(Model model) {
        model.addAttribute("userForm", new User());
        return "admin-login";
    }

    @PostMapping("/admin/submit-login")
    public String handleAdminLogin(@ModelAttribute("userForm") User userForm,
                                   HttpSession session,
                                   Model model) {

        User authenticatedUser = authService.handleLogin(userForm.getEmail(), userForm.getPassword());

        if (authenticatedUser != null && authService.isAdminAuthorized(authenticatedUser)) {
            session.setAttribute("loggedInAdmin", authenticatedUser);
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Invalid Admin Email or Password!");
        return "admin-login";
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/admin/login?error=Please login first!";
        }

        List<String> dbRoutes = routeService.getAllRouteIdsForDropdown();
        model.addAttribute("availableRoutes", dbRoutes);

        return "admin-dashboard";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login?msg=Logged out successfully";
    }

    @GetMapping("/admin/api/alerts")
    @ResponseBody
    public List<Map<String, Object>> fetchLiveAlerts() {
        System.out.println("🚀 [Live Check]: Admin dashboard background polling hit!");
        return driverService.getActiveAlerts();
    }

    @GetMapping("/student/api/get-notice")
    @ResponseBody
    public String getStudentNotice() {
        String notice = studentService.getNoticeForStudentDashboard("ALL");
        if (notice == null || notice.trim().isEmpty()) {
            return "NO_NEW_NOTICE";
        }
        return notice.trim();
    }

    @PostMapping("/admin/api/alerts/resolve")
    @ResponseBody
    public String resolveLiveAlert(@RequestParam("id") int id) {
        boolean success = driverService.clearEmergencyAlert(id);
        return success ? "SUCCESS" : "ERROR";
    }

    @PostMapping("/admin/api/send-notice")
    @ResponseBody
    public String sendNoticeBroadcast(@RequestParam("route") String route, @RequestParam("message") String message) {
        if (message == null || message.trim().isEmpty()) {
            return "ERROR: Message is empty";
        }

        boolean success = studentService.saveAdminNotice(route.trim(), message.trim());

        if (success) {
            System.out.println("Notice successfully saved in DB: " + message);
            return "SUCCESS";
        } else {
            System.out.println(" Failed to save notice in Database!");
            return "ERROR: Database insertion failed";
        }
    }
}