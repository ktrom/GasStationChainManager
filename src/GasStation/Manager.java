package GasStation;

import Controllers.ChainManagementController;
import Controllers.ScheduleController;
import DatabaseClasses.DatabaseSetup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Manager {

    public static void main(String[] args) throws SQLException {

//        // Initial database setup
//        Connection conn = Utilities.getConnection();
//        DatabaseSetup.initializeDatabase(conn);
//
//        GasStation g = new GasStation("mamacita");
//        g.create();
//        g.pull();
//
//        String hireDate = "1996-06-19";
//        Date    d = Date.valueOf(hireDate);
//        Employee m = new Employee(g.getGasStationID(), "Ronson", "123456789", 25.6, "managerial", EmployeePosition.MANAGER, d);
//        m.create();
//        m.pull();
//
//        hireDate = "2020-01-25";
//        d = Date.valueOf(hireDate);
//        Employee attendant = new Employee(m.getGasStationID(), "kyle", "0123567891", 2.50, "attendancy", EmployeePosition.ATTENDANT, d);
//        attendant.create();
//        attendant.pull();
//
//
//        String scheduledDay = "2020-10-14";
//        Date workDay = Date.valueOf(scheduledDay);
//        ScheduleController s = new ScheduleController();
//        s.scheduleEmployee(m.getEmployeeID(), attendant.getEmployeeID(), workDay, 1);


        // Create inventory
        Inventory i = new Inventory(1, 1, 10);

        Inventory[] inv = new Inventory[1];
        inv[0] = i;

        ChainManagementController cmc = new ChainManagementController();
        cmc.deployFleet(inv);
    }
}
