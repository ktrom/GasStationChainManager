package GasStation;

import Controllers.*;
import DatabaseClasses.DatabaseSetup;
import HelperClasses.HelperFunctions;
import UI.*;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Class representing the Gas Station Manager Application
 */
public class Manager {

    /**
     * Method to run the Gas Station Manager Application
     * @param args
     * @throws SQLException when connection fails to establish
     */
    public static void main(String[] args) throws SQLException {

        // Initial database setup
        Connection conn = Utilities.getConnection();
        DatabaseSetup.initializeDatabase(conn);

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your employee ID: ");
        int id = scan.nextInt();
        System.out.println();

        // Considering admin capabilities
        if (id != -999) {
            EmployeeController ec = new EmployeeController();
            EmployeePosition position = ec.getEmployeePosition(id);

            if (position == EmployeePosition.MANAGER) {
                ManagerUI prompt = new ManagerUI();
                prompt.handleManager(id);
            }
            if (position == EmployeePosition.ATTENDANT) {
                AttendantUI prompt = new AttendantUI();
                prompt.handleAttendant(id);
            }
            if (position == EmployeePosition.HIRING_MANAGER) {
                HiringManagerUI prompt = new HiringManagerUI();
                prompt.handleHiringManager(id);
            }
            if (position == EmployeePosition.CFO) {
                CFOUI prompt = new CFOUI();
                prompt.handleCFO(id);
            }

        } else {
            AdminUI prompt = new AdminUI();
            prompt.handleAdmin(id);
        }


    }
}
