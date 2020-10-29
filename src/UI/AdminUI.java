package UI;

import Controllers.*;
import GasStation.EmployeePosition;

import java.sql.Date;
import java.util.Scanner;

/**
 * User Interaction class for an Admin
 */
public class AdminUI {
    public void handleAdmin(int adminID){
        Scanner scan = new Scanner(System.in);
        int option = 1;
        System.out.println("Logged in as admin\n");

        while(option > -1){
            System.out.println("Enter one of the following options or -1 to exit");
            System.out.println("1: Add Quick Item");
            System.out.println("2: Add Item");
            System.out.println("3: Add Quick Employee");
            System.out.println("4: Add Employee");
            System.out.println("5: Add Transaction");
            System.out.println("6: Add Schedule");
            option = scan.nextInt();
            System.out.println();
            if(option == 1){
                addQuickItem();
            }
            if(option == 2){
                addItem();
            }
            if(option == 3){
                addQuickEmployee();
            }
            if(option == 4){
                addEmployee();
            }
            if(option == 5){
                addTransaction();
            }
            if(option == 6){
                addSchedule();
            }
        }
    }

    private void addQuickItem()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Add Quick Item");
        System.out.println("Name: ");
        String name = scan.nextLine();
        System.out.println("Price: ");
        double price = scan.nextDouble();
        System.out.println("Supplier Price: ");
        double supplierPrice = scan.nextDouble();

        ItemController ic = new ItemController();
        ic.createItem(name, price, supplierPrice, "","");
    }

    private void addItem(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Add Item");
        System.out.println("Name: ");
        String name = scan.nextLine();
        System.out.println("Price: ");
        double price = scan.nextDouble();
        System.out.println("Supplier Price: ");
        double supplierPrice = scan.nextDouble();
        System.out.println("Photourl");
        String url = scan.next();
        scan.nextLine();
        System.out.println("Notes");
        String notes = scan.nextLine();

        ItemController ic = new ItemController();
        ic.createItem(name, price, supplierPrice, url, notes);
    }

    private void addEmployee(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Add Employee");

        System.out.println("Gas Station Id: ");
        int gasStationId = scan.nextInt();
        scan.nextLine();
        System.out.println("Name: ");
        String name = scan.nextLine();

        System.out.println("SSN: ");
        String ssn = scan.next();

        System.out.println("Salary: ");
        double salary = scan.nextDouble();
        scan.nextLine();
        System.out.println("Department: ");
        String department = scan.nextLine();

        System.out.println("Position: COO, CFO, MANAGER, HIRING_MANAGER, ATTENDANT (case sensitive)");
        EmployeePosition ep = EmployeePosition.valueOf(scan.next());

        System.out.println("Start Date (yyyy-mm-dd)");
        Date date = Date.valueOf(scan.next());

        EmployeeController ec = new EmployeeController();
        ec.createEmployee(gasStationId, name, ssn, salary, department, ep, date);
    }

    private void addQuickEmployee(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Add Quick Employee");

        System.out.println("Gas Station Id: ");
        int gasStationId = scan.nextInt();
        scan.nextLine();

        System.out.println("Name: ");
        String name = scan.nextLine();

        String ssn = "";

        System.out.println("Salary: ");
        double salary = scan.nextDouble();

        String department = "";

        System.out.println("Position: COO, CFO, MANAGER, HIRING_MANAGER, ATTENDANT (case sensitive)");
        EmployeePosition ep = EmployeePosition.valueOf(scan.next());

        Date date = Date.valueOf("1970-01-01");

        EmployeeController ec = new EmployeeController();
        ec.createEmployee(gasStationId, name, ssn, salary, department, ep, date);
    }

    private void addTransaction(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Add Transaction");

        System.out.println("Enter Gas Station Id");
        int gasStationId = scan.nextInt();

        System.out.println("Item Id");
        int itemId = scan.nextInt();

        System.out.println("Quantity");
        double quantity = scan.nextDouble();

        System.out.println("Date of Transaction (yyyy-mm-dd)");
        Date date = Date.valueOf(scan.next());

        TransactionController tc = new TransactionController();
        tc.createTransaction(itemId, gasStationId, quantity, date);
    }

    private void addSchedule(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Add Schedule");

        System.out.println("Enter Gas Station Id");
        int gasStationId = scan.nextInt();

        System.out.println("Enter Employee Id");
        int employeeId = scan.nextInt();

        System.out.println("Enter Date (yyyy-mm-dd)");
        Date date = Date.valueOf(scan.next());

        System.out.println("Enter shift: 1 for 7-3, 2 for 3-11, 3 for 11-7");
        int shift = scan.nextInt();

        ScheduleController sc = new ScheduleController();
        sc.scheduleEmployee(gasStationId, employeeId, date, shift);
    }
}
