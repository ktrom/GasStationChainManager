package Commands;

import Controllers.FinancialController;
import Controllers.GasStationController;
import Controllers.TransactionController;
import HelperClasses.HelperFunctions;

import java.sql.Date;
import java.util.*;

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

                int detailedReport;
                System.out.println("Would you like a detailed report of all revenue streams?");
                System.out.println("1: Yes");
                System.out.println("2: No");
                detailedReport = scan.nextInt();

                if(detailedReport == 1){
                    System.out.println("\nSummary Report for " + gsc.getLocation(gasStationID) + " location from " + start.toString() + " to " + end.toString());
                    System.out.println("________________________________________________________________________________________");
                    System.out.println("Transaction Revenue: $" + fc.getGasStationTransactionRevenue(gasStationID, start, end));
                    System.out.println("Employee Salaries Expenditure: (-)$" + -fc.getGasStationSalaryDeduction(gasStationID, start, end));
                    TransactionController tc = new TransactionController();
                    HashMap<String, double[]> namesToValues = tc.getItemsSoldAtGasStation(gasStationID);

                    System.out.println();
                    System.out.println("Item Sales:");
                    System.out.println(" _______________________________________________");
                    System.out.println(String.format("| %13s | %13s | %13s |", "Item Name", "Quantity Sold", "Item Revenue"));
                    System.out.println("|_______________________________________________|");
                    Set<String> names = namesToValues.keySet();

                    for(String name : names){
                        System.out.println(String.format("| %13s | %13s | %13s |", name, String.format("%6.3f",namesToValues.get(name)[0]), "$"+String.format("%6.2f",namesToValues.get(name)[1])));
                    }
                    System.out.println("|_______________|_______________|_______________|");
                }
                System.out.println();
                System.out.println("Total Revenue at the " + gsc.getLocation(gasStationID) + " location from " + start.toString() + " to " + end.toString() + ": $" + fc.getGasStationRevenue(gasStationID, start, end));
                System.out.println();
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
        System.out.println("2. Calculate Revenue for Station Chain");
    }

    @Override
    public void execute() {
        viewFinancials();
    }

    @Override
    public String description() {
        return description;
    }
}
