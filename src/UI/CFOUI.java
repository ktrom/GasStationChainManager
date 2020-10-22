package UI;

import Controllers.FinancialController;
import Controllers.GasStationController;
import GasStation.GasStation;
import HelperClasses.HelperFunctions;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * User Interaction class for the CFO
 */
public class CFOUI {

    /**
     * Handles the case where the actor is a CFO
     * @param CFOID ID of CFO (Actor)
     */
    public void handleCFO(int CFOID) {
        Scanner scan = new Scanner(System.in);
        int option = 1;
        System.out.println("Logged in as CFO\n");
        while (option == 1) {
            System.out.println("Select an option or enter -1 to quit:");
            System.out.println("1. View Financials");
            option = scan.nextInt();
            if(option == -1){
                return;
            }
            if(option == 1) {
                viewFinancials();
            }
        }
    }

    /**
     * Allows the CFO user to interact with the system to view certain financials of the business
     */
    private void viewFinancials(){
        Scanner scan = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.print("Type number of operation you wish to perform or -1 to Quit: ");
                printOperations();
                int option = scan.nextInt();

                Date[] dates = HelperFunctions.getDates();
                Date start = dates[0];
                Date end = dates[1];
                FinancialController fc = new FinancialController();
                if (option == 1) {
                    System.out.print("Gas Station ID: ");
                    int gasStationID = scan.nextInt();
                    GasStationController gsc = new GasStationController();

                    System.out.println("Revenue at the " + gsc.getLocation(gasStationID)+ " location from " + start.toString() + " to " + end.toString() + ": $" + fc.getGasStationRevenue(gasStationID, start, end));
                } else if (option == 2) {
                    System.out.println("Total Chain Revenue from " + start.toString() + " to " + end.toString() + ": $" + fc.getChainRevenue(start, end));
                } else if (option == -1) {
                    System.out.println("Exiting Financial Operations");
                    exit = true;
                } else {
                    // input does not match any operation
                    System.out.println("Sorry, that operation is not available. Select from the list of available options.");
                }
            }
        }


    /**
     * Lists the operations the hiring manager has to choose from
     */
    private static void printOperations() {
        System.out.println("\n");
        // For each operation type the next number: operation
        System.out.println("Operations: ");
        System.out.println("1. Calculate Revenue for Single Station");
        System.out.println("2. Calculate Revenue for station chain");
    }
}
