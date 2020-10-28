package Commands;

import Controllers.FinancialController;
import Controllers.GasStationController;
import HelperClasses.HelperFunctions;

import java.sql.Date;
import java.util.Scanner;

public class ViewFinancialsCommand implements Command{
    private final static String description = "View Financials";

    public ViewFinancialsCommand(){

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

            Date[] dates = null;
            Date start = null;
            Date end = null;

            if(option != -1){
                dates = HelperFunctions.getDates();
                start = dates[0];
                end = dates[1];
            }
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
    private void printOperations() {
        System.out.println("\n");
        // For each operation type the next number: operation
        System.out.println("Operations: ");
        System.out.println("1. Calculate Revenue for Single Station");
        System.out.println("2. Calculate Revenue for station chain");
    }

    @Override
    public void execute() {
        printOperations();
    }

    @Override
    public String description() {
        return description;
    }
}
