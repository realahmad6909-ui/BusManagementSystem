package com.uet.busmanagement.controller;

import com.uet.busmanagement.model.Driver;
import com.uet.busmanagement.model.Student;
import com.uet.busmanagement.service.StudentService;
import com.uet.busmanagement.controller.RouteController;
import com.uet.busmanagement.controller.BookingController;
import com.uet.busmanagement.controller.ChallanController;
import java.util.List;
import java.util.Scanner;

public class StudentController {

    private static StudentController stuCtrl = new StudentController();
    private static RouteController routectrl = new RouteController();
    private static BookingController bookctrl = new BookingController();
    private static ChallanController challanctrl = new ChallanController();
    StudentService studentService = new StudentService();
    private static Scanner sc = new Scanner(System.in);
    Student student = new Student();

    public StudentController() {
        this.studentService = new StudentService();

    }

    public  void handleRegistrationFlow(String name, String email, String password, String phone, String Department,String regnum) {

        Student s = new Student();
        s.setName(name);
        s.setEmail(email);
        s.setPassword(password);
        s.setPhone(phone);
        s.setDepartment(Department);
        s.setRegNum(regnum);



        String result = studentService.registerStudent(s);
        System.out.println(result);
    }

    public Student handleGetStudentDetails(String regnum) {
        return studentService.getStudentProfileByregnum(regnum);
    }

    public void displayPendingStudents() {
        List<Student> pending = studentService.getPendingStudents();
    }

    public void approveStudent(int id) {
        if (studentService.changeStatus(id, "APPROVED")) {
            System.out.println("Student approved!");
        }
    }

    public List<Student> handleGetAllStudents() {
        return studentService.getAllStudentProfiles();
    }

    public void manageStudents() {
        System.out.println("1. View All Students | 2. Approve Student | 3. Display Pending Students | 4. Search student | 5. Delete Student ");
        int ch = sc.nextInt();

        if (ch == 1) {
            handleGetAllStudents();
        }
        if (ch == 2) {
            System.out.println("id");
            approveStudent(sc.nextInt());
        }
        if (ch == 3) {
            displayPendingStudents();
        }
        if (ch == 4) {
            System.out.println("Reg.num");
            handleGetStudentDetails(sc.next());
        }
        if(ch == 5) {
            System.out.println("E-MAIL");
            studentService.removeStudent(sc.next());
        }
    }
    // LoginController.java
    public void startLogin() {
        System.out.println("\n--- STUDENT LOGIN ---");
        System.out.print("E-MAIL: ");
        String reg = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        if (reg.isEmpty() || pass.isEmpty()) {
            System.out.println("Error: Email or Password cannot be empty!");
            return;
        }

       studentService.login(reg,pass);

        if (student != null) {
            System.out.println("Login Successful! Welcome, " + student.getName());
            stuCtrl.handleStudentDashboard();

           bookctrl.showBookingMenu(student);
        } else {
            System.out.println("Invalid Reg Number or Password!");
        }
    }

    public static void studentFlow() {
        System.out.println("1. Register | 2. Login");
        int ch = sc.nextInt();
        sc.nextLine();
        if (ch == 1) {
            System.out.print("Name, E-mail,password,phone Department,regnum");
            stuCtrl.handleRegistrationFlow(sc.next(), sc.next(), sc.next(),sc.next(),sc.next(),sc.next());
        } else {
            stuCtrl.startLogin();
        }
    }
    public void viewProfile(String regnum) {

        Student student = studentService.getStudentProfileByregnum(regnum);

        if (student != null) {
            System.out.println("\n--- MY PROFILE ---");
            System.out.println("Name: " + student.getName());
            System.out.println("Reg No: " + student.getRegNum());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Account Status: " + student.getStatus());
            System.out.println("------------------");
        } else {
            System.out.println("Error: Could not load profile.");
        }
    }
    // StudentController.java
    public void editProfile(Student student) {
        System.out.println("\n--- EDIT PROFILE ---");
        System.out.print("Enter New Name: ");
        String newName = sc.nextLine();
        System.out.print("Enter New Department: ");
        String newDept = sc.nextLine();

        student.setName(newName);
        student.setDepartment(newDept);

        String message = studentService.updateProfile(student);
        System.out.println(message);
    }
    public void handleStudentDashboard() {


        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Student DASHBOARD ---");
            System.out.println("Welcome to Student Dashboard");
            System.out.println("1. View Profile & Status");
            System.out.println("2. View Route details (Enter id such as 01)");
            System.out.println("3. Book a bus");
            System.out.println("4. View Fee Challans");
            System.out.println("5. Edit Personal Info");
            System.out.println("6. Logout");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter REG NUM") ;
                    stuCtrl.viewProfile(sc.next());
                    break;
                case 2:
                     System.out.println("Enter Roude id");
                     routectrl.handleShowRouteDetails(sc.next());
                     break;
                case 3:
                    bookctrl.showBookingMenu(student);
                case 4:
                    System.out.println("Enter reg-num");
                    challanctrl.handleGetStudentChallans(sc.next());
                     break;
                case 5: editProfile(student); break;
                case 6: exit = true; break;
            }
        }
    }
}

