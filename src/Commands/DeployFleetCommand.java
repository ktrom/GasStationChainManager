package Commands;

import Controllers.ChainManagementController;
import Controllers.TransactionController;
import GasStation.Inventory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Scanner;

public class DeployFleetCommand implements Command {
    private final static String description = "Deploy Fleet";
    private int cooID;

    public DeployFleetCommand(int cooID) {
        this.cooID = cooID;
    }

    private boolean deployFleet() {
        Scanner scan = new Scanner(System.in);
        ChainManagementController cmc = new ChainManagementController();

        System.out.println("Enter the gas station id:");
        int gasStationID = scan.nextInt();

        System.out.println("Enter the item id that is being restocked:");
        int itemID = scan.nextInt();

        System.out.println("Enter the number of units to restock:");
        int quantity = scan.nextInt();

        try {
            boolean success = cmc.deployFleet(new Inventory(gasStationID, itemID, quantity));

            if (!success) {
                System.out.println("Gas station " + gasStationID + " doesn't currently sell item " + itemID + ".");
                System.out.println("Select an option or enter -1 to quit:\n1. Add item " + itemID + " to stock of gas station " + gasStationID + "\n2. Cancel transaction");

                int addItemOption = scan.nextInt();

                if (addItemOption == 1) {
                    Inventory inv = new Inventory(gasStationID, itemID, quantity);
                    inv.create();
                } else if (addItemOption == 2) {
                    return false;
                } else {
                    System.exit(0);
                }
            }
        } catch (SQLException e) {
            System.out.println("System error - please try again.");
            System.exit(1);
        }

        // Log the transaction
        TransactionController tc = new TransactionController();
        boolean complete = tc.createTransaction(itemID, gasStationID, quantity, new Date(Calendar.getInstance().getTime().getTime()));
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
        deployFleet();
    }

    @Override
    public String description() {
        return description;
    }
}
