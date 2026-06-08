package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Stops;
import com.uet.busmanagement.model.Student;
import com.uet.busmanagement.model.Route;
import com.uet.busmanagement.model.Complaint;
import com.uet.busmanagement.model.User;
import com.uet.busmanagement.model.Bus;
import com.uet.busmanagement.model.Challan;
import com.uet.busmanagement.service.StudentService;
import com.uet.busmanagement.service.RouteService;
import com.uet.busmanagement.service.BookingService;
import com.uet.busmanagement.service.ChallanService;
import com.uet.busmanagement.service.ComplaintService;
import com.uet.busmanagement.service.StopsService;
import com.uet.busmanagement.service.BusService;
import com.uet.busmanagement.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/students") // 👈 BASE PATH IS "/students"
public class StudentController {

    private final StudentService studentService;
    private final RouteService routeService;
    private final StopsService stopService;
    private final BookingService bookingService;
    private final ChallanService challanService;
    private final BusService busService;
    private final AuthService authService; // 👈 Injecting AuthService properly

    @Autowired
    public StudentController(StudentService studentService, RouteService routeService,
                             StopsService stopService, BookingService bookingService,
                             ChallanService challanService, BusService busService,
                             AuthService authService) { // 👈 Added in constructor
        this.studentService = studentService;
        this.routeService = routeService;
        this.stopService = stopService;
        this.bookingService = bookingService;
        this.challanService = challanService;
        this.busService = busService;
        this.authService = authService;
    }


    @GetMapping("/manage")
    public String manageStudents(@RequestParam(value = "status", required = false) String status, Model model) {
        List<Student> students;
        if ("PENDING".equalsIgnoreCase(status)) {
            students = studentService.getPendingStudents();
            model.addAttribute("statusFilter", "PENDING");
        } else {
            students = studentService.getAllStudentProfiles();
            model.addAttribute("statusFilter", "all");
        }
        model.addAttribute("students", students);
        return "manage-students";
    }

    @GetMapping("/approve")
    public String approveStudent(@RequestParam("id") int id) {
        studentService.changeStatus(id, "APPROVED");
        return "redirect:/students/manage?status=PENDING";
    }

    @GetMapping("/search")
    public String searchStudent(@RequestParam("keyword") String keyword, Model model) {
        List<Student> students = new java.util.ArrayList<>();
        Student student = studentService.getStudentProfileByregnum(keyword);
        if (student != null) {
            students.add(student);
        }
        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        model.addAttribute("statusFilter", "search");
        return "manage-students";
    }

    @GetMapping("/delete")
    public String deleteStudent(@RequestParam("email") String email) {
        studentService.removeStudent(email);
        return "redirect:/students/manage";
    }



    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "msg", required = false) String msg, Model model) {
        if (msg != null) {
            model.addAttribute("message", msg);
        }
        return "student-login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {
        Student student = studentService.login(email.trim(), password);
        if (student != null) {
            session.setAttribute("loggedInStudent", student);
            return "redirect:/students/dashboard";
        } else {
            model.addAttribute("error", "Invalid Email Credentials or Password!");
            return "student-login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("studentForm", new Student());
        return "student-register";
    }
    @GetMapping("/profile-edit")
    public String showEditForm(HttpSession session, Model model) {
        Student currentStudent = (Student) session.getAttribute("loggedInStudent");
        if (currentStudent == null) {
            return "redirect:/students/login";
        }

        Student detailedStudent = studentService.getStudentProfileByregnum(currentStudent.getRegNum());
        model.addAttribute("student", detailedStudent);
        return "student-profile-edit";
    }

    @PostMapping("/submit-register") // Base path /students laga hua hai, to ye /students/submit-register hi banega
    public String handleStudentRegistration(@ModelAttribute("studentForm") Student studentForm, Model model) {

        studentForm.setRole("STUDENT");
        studentForm.setStatus("PENDING"); // Taake admin approve kar sakay

        String result = authService.registerUser(studentForm);

        if ("Success".equalsIgnoreCase(result) || result == null || result.toLowerCase().contains("success")) {
            return "redirect:/students/login?msg=Registered Successfully! Waiting for Admin Approval.";
        } else {
            model.addAttribute("error", result);
            return "student-register";
        }
    }

    @GetMapping("/dashboard")
    public String studentDashboard(HttpSession session, Model model) {
        // 1. Session se student uthein
        Student sessionStudent = (Student) session.getAttribute("loggedInStudent");

        // 2. Agar session khali hai toh login par bhej dein
        if (sessionStudent == null) {
            return "redirect:/students/login?msg=Please login first.";
        }

        // 🌟 REFRESH DATA FROM DATABASE (Taake status aur challan real-time aayein)
        Student freshStudent = null;
        List<Challan> studentChallans = new ArrayList<>();

        try {

            freshStudent = studentService.getStudentProfileById(sessionStudent.getStudentId());

            if (freshStudent != null && freshStudent.getRegNum() != null) {
                studentChallans = challanService.getStudentChallans(freshStudent.getRegNum());
            }
        } catch (Exception e) {
            System.out.println("Error fetching fresh student/challan data: " + e.getMessage());
        }


        if (freshStudent == null) {
            freshStudent = sessionStudent;
        }

        model.addAttribute("student", freshStudent);
        model.addAttribute("challans", studentChallans);

        return "student-dashboard";
    }

    @GetMapping("/challans")
    public String viewStudentChallans(HttpSession session, Model model) {
        // 1. Session se logged-in student uthein
        Student currentStudent = (Student) session.getAttribute("loggedInStudent");

        // 2. Agar session khali hai toh wapis login par bhej dein
        if (currentStudent == null) {
            return "redirect:/students/login?msg=Please login first.";
        }

        List<Challan> studentChallans = new ArrayList<>();
        try {
            if (currentStudent.getRegNum() != null) {
                studentChallans = challanService.getStudentChallans(currentStudent.getRegNum());
            }
        } catch (Exception e) {
            System.out.println("Challan Page Fetch Error: " + e.getMessage());
        }

        model.addAttribute("student", currentStudent);
        model.addAttribute("challans", studentChallans);

        return "student-challan";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    // ==========================================
    // STUDENT DASHBOARD FUNCTIONALITIES
    // ==========================================

    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        Student currentStudent = (Student) session.getAttribute("loggedInStudent");
        if (currentStudent == null) return "redirect:/students/login";

        Student detailedStudent = studentService.getStudentProfileByregnum(currentStudent.getRegNum());
        model.addAttribute("student", detailedStudent);
        return "student-profile-view";
    }

    @GetMapping("/routes")
    public String viewRoutes(HttpSession session, Model model) {
        if (session.getAttribute("loggedInStudent") == null) {
            return "redirect:/students/login";
        }

        List<Route> allRoutes = routeService.displayAllRoutes();
        Map<String, List<Stops>> routeStopsMap = new HashMap<>();

        for (Route route : allRoutes) {
            List<Stops> stopsForThisRoute = stopService.getStopsByRouteId(route.getRouteId());
            routeStopsMap.put(route.getRouteId(), stopsForThisRoute);
        }

        model.addAttribute("routes", allRoutes);
        model.addAttribute("stopsMap", routeStopsMap);
        return "student-routes-view";
    }

    @GetMapping("/book")
    public String bookBusForm(HttpSession session, Model model) {
        if (session.getAttribute("loggedInStudent") == null) return "redirect:/students/login";

        List<Route> allRoutes = routeService.displayAllRoutes();

        try {
            List<Bus> allBuses = busService.getActiveBuses();
            if (allBuses != null && allRoutes != null) {
                for (Route r : allRoutes) {
                    for (Bus b : allBuses) {
                        if (b.getRoute() != null && String.valueOf(b.getRoute().getRouteId()).equals(r.getRouteId())) {
                            int combinedCapacity = bookingService.getRouteCapacity(r.getRouteId());
                            if (combinedCapacity > 0) {
                                b.setSeatingCapacity(combinedCapacity);
                            }
                            r.setBus(b);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Bus association failed: " + e.getMessage());
        }

        model.addAttribute("routes", allRoutes);
        return "student-bus-booking";
    }

    @PostMapping("/book")
    public String processBooking(@RequestParam("routeId") String routeId,
                                 HttpSession session,
                                 Model model) {
        if (session.getAttribute("loggedInStudent") == null) {
            return "redirect:/students/login";
        }

        Student currentStudent = (Student) session.getAttribute("loggedInStudent");
        String regNum = currentStudent.getRegNum();


        if (challanService.hasUnpaidChallan(regNum)) {
            return "redirect:/book?error=ACCESS DENIED: Please pay your outstanding challan first.";
        }

        try {
            String resultMessage = bookingService.processBooking(regNum, routeId);
            if ("Booking successful!".equalsIgnoreCase(resultMessage) || resultMessage.toLowerCase().contains("success")) {
                String displayId = "UET-BK-" + (10000 + new java.util.Random().nextInt(90000));
                return "redirect:/book?msg=Seat booked successfully! Reference ID: " + displayId;
            } else {
                return "redirect:/students/book?error=" + resultMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return "redirect:/students/book?error=" ;
        }
    }

    @PostMapping("/complain")
    public String handleRouteComplain(@RequestParam("complainRouteId") String routeId,
                                      HttpSession session) {
        if (session.getAttribute("loggedInStudent") == null) {
            return "redirect:/students/login";
        }

        Student currentStudent = (Student) session.getAttribute("loggedInStudent");
        String regNum = currentStudent.getRegNum();

        try {
            ComplaintService complaintService = new ComplaintService();
            String response = complaintService.registerComplaint(regNum, routeId);

            if ("Success".equalsIgnoreCase(response) || response.toLowerCase().contains("success")) {
                return "redirect:/students/book?msg=Complain registered successfully for Route " + routeId + "!";
            } else {
                return "redirect:/students/book?error=Failed to store complaint.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/students/book?error=Internal controller routing failure.";
        }
    }
    @PostMapping("/profile-edit")
    public String processProfileUpdate(@RequestParam("name") String name,
                                       @RequestParam("department") String department,
                                       HttpSession session) {
        Student currentStudent = (Student) session.getAttribute("loggedInStudent");
        if (currentStudent == null) {
            return "redirect:/students/login";
        }

        try {

            Student updatedStudent = new Student();
            updatedStudent.setRegNum(currentStudent.getRegNum()); // Locked field
            updatedStudent.setName(name);
            updatedStudent.setDepartment(department);


            studentService.updateProfile(updatedStudent);


            currentStudent.setName(name);
            currentStudent.setDepartment(department);
            session.setAttribute("loggedInStudent", currentStudent);

            return "redirect:/students/dashboard?msg=Profile updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/students/dashboard?error=Profile update failed!";
        }
    }
}