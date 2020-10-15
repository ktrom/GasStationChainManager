package GasStation;

import Controllers.*;
import DatabaseClasses.DatabaseSetup;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Manager {

    public static void main(String[] args) throws SQLException {

        // Initial database setup
        Connection conn = Utilities.getConnection();
        DatabaseSetup.initializeDatabase(conn);

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your employee ID: ");
        int id = scan.nextInt();



    }


    COO,
    CFO,
    MANAGER,
    HIRING_MANAGER,
    ATTENDANT

    private void handleCOOOptions() {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Deploy Fleet");
        int choice = scan.nextInt();
        if(choice == 1){
            ChainManagementController c = new ChainManagementController();

            System.out.println("Enter Gas Station ID: ");
            int gasStationId = scan.nextInt();

            System.out.println("");

            c.deployFleet()
        }

    };
    private void printCFOOptions{
        System.out.println("1. Total Weekly Revenue");
        System.out.println("2. Total Weekly Revenue");
    }
}
