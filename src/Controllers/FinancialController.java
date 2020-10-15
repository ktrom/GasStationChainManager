package Controllers;

import java.sql.Date;

import java.sql.SQLException;
import java.util.Scanner;

public class FinancialController {

    Date start;
    Date end;

    /**
     * Lists the operations the hiring manager has to choose from
     */
    private void printOperations() {
        System.out.println("\n");
        // For each operation type the next number: operation
        System.out.println("Operations: ");
        System.out.println("0. Calculate Revenue for Single Station");
        System.out.println("1. Calculate Revenue for station chain");
    }

    /**
     * All available operations an employee can perform
     * User types number corresponding to the operation they wish to perform
     * User can perform operations until they quit by typing -1
     * @throws SQLException
     */
    public void hiringManagerOptions() throws SQLException {
        System.out.println("\n\n");
        Scanner s = new Scanner(System.in);
        // Check desired input against available operations
        boolean exit = false;
        while (!exit) {
            printOperations();
            System.out.print("Type number of operation you wish to perform or -1 to quit: ");
            int input = s.nextInt(); // user input integer
            if (input == 1) {
                calculateStationRev(s);
            } else if(input == 2) {
                calculateChainRev(s);
            } else if(input == -1){
                System.out.println("Exiting Financial Operations");
                exit = true;
            } else {
                // input does not match any operation
                System.out.println("Sorry, that operation is not available. Select from the list of available options.");
            }
        }
    }

    private void calculateChainRev(Scanner s) {
        getDates(s);

    }

    private void calculateStationRev(Scanner s) {
        getDates(s);
    }

    private void getDates(Scanner s) {
        // get start date from user
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Please enter start date you wish to see revenue from (yyyy-mm-dd: ");
            String dateString = s.next();
            String[] date = dateString.split("-");
            if (date.length == 3 && Integer.parseInt(date[0]) > 999 && Integer.parseInt(date[0]) < 10000 && Integer.parseInt(date[1]) > 0 && Integer.parseInt(date[1]) < 13 && Integer.parseInt(date[2]) > 0 && Integer.parseInt(date[2]) < 32) {
                this.start = Date.valueOf(dateString);
                validDate = true;
            } else {
                System.out.println("Sorry, that's an invalid date. Type it as yyyy-mm-dd");
            }
        }

        // get end date from user
        validDate = false;
        while (!validDate) {
            System.out.print("Please enter end date you wish to see revenue from (yyyy-mm-dd: ");
            String dateString = s.next();
            String[] date = dateString.split("-");
            if (date.length == 3 && Integer.parseInt(date[0]) > 999 && Integer.parseInt(date[0]) < 10000 && Integer.parseInt(date[1]) > 0 && Integer.parseInt(date[1]) < 13 && Integer.parseInt(date[2]) > 0 && Integer.parseInt(date[2]) < 32) {
                this.end = Date.valueOf(dateString);
                validDate = true;
            } else {
                System.out.println("Sorry, that's an invalid date. Type it as yyyy-mm-dd");
            }
        }
    }

}
