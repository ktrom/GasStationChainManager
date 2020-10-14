package GasStation;

import DatabaseClasses.DatabaseSetup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Manager {

    public static void main(String[] args) throws SQLException {

        // Initial database setup
        Connection conn = Utilities.getConnection();
        DatabaseSetup.initializeDatabase(conn);



        // Test Employee:
//        Employee e = new Employee(1, "Eli", "123-12-1234", 4900.39, "Cleaning", EmployeePosition.ATTENDANT, Date.valueOf("2020-10-14"));
//        System.out.println(e.getEmployeeID());
//        e.create();

//        e.setEmployeePosition(EmployeePosition.COO);
//        System.out.println(e.getEmployeePosition());

//        Employee emp = new Employee(5);
//        System.out.println(emp.getName());
//        System.out.println(emp.getSalary());
//        System.out.println(emp.getSSN());
//        System.out.println(emp.getGasStationID());

        // Test Item:
//        Item i = new Item("Pizza", 12.34, "www.google.com/pizza", "Standard large pizza.");
//        System.out.println(i.getItemID());

//        Item i2 = new Item(1);
//        System.out.println(i2.getName());
//        System.out.println(i2.getItemID());
//        i2.setName("Breakfast Pizza");

//        Item i3 = new Item(1);
//        System.out.println(i3.getName());

        // Test Inventory:
//        Inventory i = new Inventory(1, 1, 10);
//        System.out.println(i.getGasStationID());

//        Inventory i2 = new Inventory(1, 1);
//        System.out.println(i2.getQuantity());

//        i2.setQuantity(0);
//        System.out.println(i2.getQuantity());

        // Test Schedule:
//        Schedule schedule = new Schedule(1, 1, Date.valueOf("2020-10-14"), "1");
//        System.out.println(schedule.getShift());

//        Schedule sc2 = new Schedule(1, 1);
//        sc2.setShift("3");

//        Schedule sc3 = new Schedule(1, 1);
//        System.out.println(sc3.getShift());
    }
}
