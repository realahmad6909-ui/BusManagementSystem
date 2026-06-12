//package com.uet.busmanagement.controller;
//
//import com.uet.busmanagement.model.User;
//import com.uet.busmanagement.service.AuthService;
//import com.uet.busmanagement.service.StudentService;
//import com.uet.busmanagement.controller.RouteController;
//
//import java.util.Scanner;
//
//public class AuthController {
//
//    private AuthService authService;
//    private static AuthController authCtrl = new AuthController();
//    private static BusController busCtrl = new BusController();
//    private static RouteController routeCtrl = new RouteController();
//    private static StopsController stopCtrl = new StopsController();
//    private static StudentController stuCtrl = new StudentController();
//    private static ChallanController challanCtrl = new ChallanController();
//    private static DriverController driverCtrl = new DriverController();
//    private static ComplaintController complCtrl = new ComplaintController();
//    private static Scanner sc = new Scanner(System.in);
//
//    public AuthController() {
//        this.authService = new AuthService();
//    }
//
//    public String handleRegistration(int userId, String name, String email, String password, String role, String phone, String profilePicPath) {
//        User newUser = new User();
//        newUser.setUserId(userId);
//        newUser.setName(name);
//        newUser.setEmail(email);
//        newUser.setPassword(password);
//        newUser.setRole(role);
//        newUser.setPhone(phone);
//        newUser.setProfilePicPath(profilePicPath);
//
//        return authService.registerUser(newUser);
//    }
//
//    public User handleLogin(String email, String password) {
//
//        return authService.handleLogin(email, password);
//    }
//
//    private StudentService studentService = new StudentService(); // Admin functions ke liye
//
//    public void handleAdminDashboard() {
//
//
//        Scanner sc = new Scanner(System.in);
//        boolean exit = false;
//
//        while (!exit) {
//            System.out.println("\n--- ADMIN DASHBOARD ---");
//            System.out.println("Welcome to Admin Dashboard");
//                    System.out.println("1. Manage Students | 2. Manage Buses | 3. Manage Routes 4.| Manage Drivers | 5. Manage Challans | 6. View / Resolve Complaints | 7. Logout");
//            int choice = sc.nextInt();
//
//            switch (choice) {
//                 case 1: stuCtrl.manageStudents(); break;
////                 case 2: busCtrl.manageBuses(); break;
////                 case 3: routeCtrl.manageRoutes(); break;
//                 case 4: driverCtrl.manageDrivers();break;
//                 case 5: challanCtrl.manageChallans();break;
//                 case 6 :
//                     complCtrl.showAdminComplaintMenu();break;
//                 case 7:
//                    exit = true;
//                    break;
//            }
//        }
//
//    }
//    public static void loginFlow(String role) {
//        System.out.print("Email: "); String email = sc.nextLine();
//        System.out.print("Pass: "); String pass = sc.nextLine();
//        User user = authCtrl.handleLogin(email, pass);
//
//        if (user != null && user.getRole().equalsIgnoreCase(role)) {
//            if (role.equals("ADMIN")) authCtrl.handleAdminDashboard();
//            else if (role.equals("DRIVER")) {
//                driverCtrl.showDriverMenu();
//            };
//        } else {
//            System.out.println("Access Denied!");
//        }
//    }
//
//}

//package com.uet.busmanagement.controller;
//
//
//import com.uet.busmanagement.model.User;
//import com.uet.busmanagement.repository.UserRepository;
//import com.uet.busmanagement.service.AuthService;
//import org.springframework.ui.Model; // ◄── YEH SAHI HAI
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*") // Frontend connectivity ke liye zaroori h
//public class AuthController {
//
//
//    // Aapki UserRepository ka object banaya
//    private final UserRepository userRepository = new UserRepository();
//
//    @GetMapping("/admin/login")
//    public String showAdminLoginPage() {
//        return "admin-login"; // templates/admin-login.html khulega
//    }
//    @PostMapping("/admin/login")
//    public String handleAdminLogin(@RequestParam String email,
//                                   @RequestParam String password,
//                                   Model model) {
//
//        // 1. Database se is email ka user uthao aapke method ke zariye
//        User user = userRepository.findByEmail(email);
//
//        // 2. Check karo ke user database mien exist karta hai ya nahi
//        if (user != null) {
//
//            // 3. Check karo ke password sahi hai aur uska ROLE "ADMIN" hai
//            // Note: Agar database mien role 'admin' (small letters) hai to ignorecase use karlein
//            if (user.getPassword().equals(password) && "ADMIN".equalsIgnoreCase(user.getRole())) {
//
//                return "admin-dashboard"; // Direct premium maroon dashboard khul jayega!
//            }
//        }
//
//        // 4. Agar user nahi mila, password galat hai, ya role admin nahi hai
//        model.addAttribute("error", "Invalid Admin Email or Password!");
//        return "admin-login";
//    }
//}

//
//    private AuthService authService;
//
//    public AuthController() {
//        this.authService = new AuthService();
//    }
//
//    // --- 1. USER REGISTRATION ENDPOINT (POST REQUEST) ---
//    // URL: http://localhost:8080/api/auth/register
//    @PostMapping("/register")
//    public String handleRegistration(@RequestParam int userId,
//                                     @RequestParam String name,
//                                     @RequestParam String email,
//                                     @RequestParam String password,
//                                     @RequestParam String role,
//                                     @RequestParam String phone,
//                                     @RequestParam String profilePicPath) {
//
//        User newUser = new User();
//        newUser.setUserId(userId);
//        newUser.setName(name);
//        newUser.setEmail(email);
//        newUser.setPassword(password);
//        newUser.setRole(role);
//        newUser.setPhone(phone);
//        newUser.setProfilePicPath(profilePicPath);
//
//        return authService.registerUser(newUser); // Service ka success message return karega
//    }
//
//    // --- 2. USER LOGIN ENDPOINT (POST REQUEST) ---
//    // URL: http://localhost:8080/api/auth/login
//    @PostMapping("/login")
//    public User handleLogin(@RequestParam String email, @RequestParam String password) {
//        User user = authService.handleLogin(email, password);
//
//        // Agar user mil jata hai to Spring Boot iska poora object (id, name, role)
//        // JSON bana kar frontend ko de dega. Frontend role dekh kar khud dashboard par redirect karega.
//        return user;
//    }
////    @GetMapping("/admin/dashboard")
////    public String showAdminDashboard() {
////        return "admin-dashboard";
////    }
//}
