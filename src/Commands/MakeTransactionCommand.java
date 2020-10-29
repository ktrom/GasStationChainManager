package Commands;

import Controllers.EmployeeController;
import Controllers.SaleController;
import Controllers.TransactionController;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MakeTransactionCommand implements Command {
    private final static String description = "Make Transaction";
    private int attendantID;

    public MakeTransactionCommand(int attendantID){
        this.attendantID = attendantID;
    }
    /**
     * Enables an Attendant to process a transaction
     * @return true if transaction was successfully recorded, false otherwise
     */
    private boolean makeTransaction(){
        Scanner scan = new Scanner(System.in);
        EmployeeController ec = new EmployeeController();

        int gasStationID = ec.getGasStationID(attendantID);

        System.out.println("Enter the item id:");
        int itemId = scan.nextInt();

        System.out.println("Enter the quantity that is being purchased:");
        int quantity = scan.nextInt();

        SaleController sc = new SaleController();
        int numSold;
        try {
            numSold = sc.sellItem(gasStationID, itemId, quantity);
        } catch (SQLException e) {
            System.out.println("System error.");
            return false;
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime todaysDate = LocalDateTime.now();
        String k = todaysDate.toString();
        Date d = Date.valueOf(todaysDate.toString().substring(0,10));

        TransactionController tc = new TransactionController();
        boolean complete = tc.createTransaction(itemId, gasStationID, numSold, d);
        if(complete){
            System.out.println("Transaction recorded!\n\n");
        }
        else{
            System.out.println("Transaction failed to be recorded.");
        }

        return complete;
    }

    @Override
    public void execute() {
        makeTransaction();
    }

    @Override
    public String description() {
        return description;
    }
}
