package GasStation;

import DatabaseClasses.DatabaseSetup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

public class Manager {

    public static void main(String[] args) throws SQLException {

        // Initial database setup
        Connection conn = Utilities.getConnection();
        DatabaseSetup.initializeDatabase(conn);



        // Test Employee:
//        Employee e = new Employee(1, "Eli", "123-12-1234", 4900.39, "Cleaning", EmployeePosition.ATTENDANT, Date.valueOf("2020-10-14"));
//        System.out.println(e.getEmployeeID());
//
//        e.setEmployeePosition(EmployeePosition.COO);
//        System.out.println(e.getEmployeePosition());

//        Employee emp = new Employee(5);
//        System.out.println(emp.getName());
//        System.out.println(emp.getSalary());
//        System.out.println(emp.getSSN());
//        System.out.println(emp.getGasStationID());
    }
}
