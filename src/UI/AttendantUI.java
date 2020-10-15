package UI;

import Controllers.EmployeeController;
import Controllers.SaleController;
import Controllers.TransactionController;
import GasStation.Employee;
import GasStation.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * User Interface class for Gas Station Attendants
 */
public class AttendantUI {

    /**
     * Handles the case where the actor is an attendant
     * @param attendantID ID of Attendant (Actor)
     */
    public void handleAttendant(int attendantID) {
        Scanner scan = new Scanner(System.in);
        int option = 1;
        System.out.println("Logged in as Attendant\n");

        while (option == 1) {

            System.out.println("Select an option or enter -1 to quit:");
            System.out.println("1. Make Transaction");

            option = scan.nextInt();

            if (option == 1) {
                makeTransaction(attendantID);
            }
        }
    }

    /**
     * Enables an Attendant to process a transaction
     * @param attendantID ID of Attendant (Actor)
     * @return true if transaction was successfully recorded, false otherwise
     */
    private boolean makeTransaction(int attendantID){
        Scanner scan = new Scanner(System.in);
        EmployeeController ec = new EmployeeController();

        int gasStationID = ec.getGasStationID(attendantID);

        System.out.println("Enter the item id:");
        int itemId = scan.nextInt();

        System.out.println("Enter the quantity that is being purchased:");
        int quantity = scan.nextInt();

        SaleController sc = new SaleController();
        try {
            sc.sellItem(gasStationID, itemId, quantity);
        } catch (SQLException e) {
            System.out.println("System error.");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime todaysDate = LocalDateTime.now();
        String k = todaysDate.toString();
        Date d = Date.valueOf(todaysDate.toString().substring(0,10));

        TransactionController tc = new TransactionController();
        boolean complete = tc.createTransaction(itemId, gasStationID, quantity, d);
        if(complete){
            System.out.println("Transaction recorded!\n\n");
        }
        else{
            System.out.println("Transaction failed to be recorded.");
        }

        return complete;
    }

}
