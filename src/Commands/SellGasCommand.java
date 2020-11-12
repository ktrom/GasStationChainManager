package Commands;

import Controllers.EmployeeController;
import Controllers.SaleController;
import Controllers.TransactionController;
import GasStation.GasStation;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SellGasCommand implements Command {
    private final static String description = "Sell Gas";
    private int attendantId;

    public SellGasCommand(int attendantId) {
        this.attendantId = attendantId;
    }

    private boolean sellGas() {
        // Get a controller to execute the gas sale
        SaleController sc = new SaleController();

        // Get the gas station ID
        EmployeeController ec = new EmployeeController();
        int GasStationID = ec.getGasStationID(this.attendantId);

        // Get user input fields
        Scanner scan = new Scanner(System.in);

        System.out.println("\nEnter the gas item ID:");
        int GasItemID = scan.nextInt();

        System.out.println("\nEnter the quantity to sell:");
        int Quantity = scan.nextInt();

        int quantitySold = 0;
        try {
            quantitySold = sc.sellGas(GasStationID, GasItemID, Quantity);
            if (quantitySold <= 0) {
                System.out.println("The gas of type " + GasItemID + " is currently out of stock.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("\nSystem error - please try again.");
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            return false;
        }

        // Log the transaction
        LocalDateTime todaysDate = LocalDateTime.now();
        Date d = Date.valueOf(todaysDate.toString().substring(0,10));
        TransactionController tc = new TransactionController();
        boolean complete = tc.createTransaction(GasItemID, GasStationID, quantitySold, d);
        if(complete){
            System.out.println("\nTransaction recorded!\n");
        }
        else{
            System.out.println("\nTransaction failed to be recorded.\n");
        }

        return true;
    }

    @Override
    public void execute() {
        sellGas();
    }

    @Override
    public String description() {
        return description;
    }
}
