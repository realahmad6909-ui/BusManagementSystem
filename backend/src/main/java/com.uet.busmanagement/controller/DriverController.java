package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.model.Student;
import com.uet.busmanagement.model.User;
import com.uet.busmanagement.repository.DriverRepository;
import com.uet.busmanagement.service.DriverService;
import com.uet.busmanagement.service.AuthService;
import com.uet.busmanagement.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService = new DriverService();

    @GetMapping("/manage")
    public String manageDrivers(Model model) {
        List<Driver> drivers = driverService.alldriverdetails();
        model.addAttribute("drivers", drivers);
        return "manage-drivers";
    }

    @PostMapping("/add")
    public String addDriver(@RequestParam("driverName") String name,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            @RequestParam("licenseNumber") String license,
                            @RequestParam("phoneNumber") String phone) {


        Driver driver = new Driver();
        driver.setName(name);
        driver.setEmail(email);
        driver.setPassword(password);
        driver.setLicenseNumber(license);
        driver.setPhone(phone);

        driverService.registerDriver(driver);
        return "redirect:/drivers/manage";
    }

    @GetMapping("/toggle-status")
    public String toggleStatus(@RequestParam("id") int id,
                               @RequestParam("currentStatus") String currentStatus) {


        String newStatus = "Active".equalsIgnoreCase(currentStatus) ? "Inactive" : "Active";

        driverService.status_update(id, newStatus);
        return "redirect:/drivers/manage";
    }

    @GetMapping("/delete")
    public String deleteDriver(@RequestParam("id") int id) {
        driverService.removeDriver(id);
        return "redirect:/drivers/manage";
    }

        @Autowired
        private AuthService userService;

        @GetMapping("/login")
        public String showDriverLogin() {
            return "driver-login";
        }
        @PostMapping("/login")
        public String processDriverLogin(@RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         HttpSession session,
                                         Model model) {

            User user = userService.handleLogin(email, password);

            if (user != null && "DRIVER".equalsIgnoreCase(user.getRole())) {

                Driver driver = driverService.getDriverByEmail(user.getEmail());

                session.setAttribute("loggedInDriver", driver);

                return "redirect:/drivers/dashboard"; // Dashboard par bhej diya
            } else {
                model.addAttribute("error", "Invalid Driver Credentials or Access Denied!");
                return "driver-login";
            }
        }
    @GetMapping("/dashboard")
    public String showDriverDashboard(HttpSession session, Model model) {
        Driver currentDriver = (Driver) session.getAttribute("loggedInDriver");

        if (currentDriver == null) {
            return "redirect:/drivers/login";
        }

        com.uet.busmanagement.model.Bus assignedBus = driverService.getAssignedBus(currentDriver.getUserId());

        model.addAttribute("driver", currentDriver);
        model.addAttribute("bus", assignedBus);
        return "driver-dashboard";
    }
    @PostMapping("/trigger-sos")
    @ResponseBody
    public String triggerSOS(HttpSession session) {
        System.out.println("Backend hit received for SOS via Service Layer!");

        Driver currentDriver = (Driver) session.getAttribute("loggedInDriver");
        if (currentDriver == null) {
            return "UNAUTHORIZED";
        }

        try {
            com.uet.busmanagement.model.Bus bus = driverService.getAssignedBus(currentDriver.getUserId());
            String busNumber = (bus != null) ? bus.getBusNumber() : "NO BUS ASSIGNED";

            boolean success = driverService.recordSOSAlert(currentDriver.getUserId(), busNumber);

            return success ? "SUCCESS" : "ERROR_SAVING";
        } catch (Exception e) {
            e.printStackTrace();
            return "EXCEPTION: " + e.getMessage();
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute("loggedInDriver");
        }
        return "redirect:/drivers/login";
    }
}