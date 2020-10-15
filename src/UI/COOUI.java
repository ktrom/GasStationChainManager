package UI;

import Controllers.ChainManagementController;
import Controllers.EmployeeController;
import Controllers.TransactionController;
import GasStation.Inventory;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class COOUI {

    public void handleCOO(int cooID) {
        Scanner scan = new Scanner(System.in);
        int option = 1;
        System.out.println("Logged in as COO\n");

        while (option == 1) {

            System.out.println("Select an option or enter -1 to quit:");
            System.out.println("1. Deploy fleet");

            option = scan.nextInt();

            if (option == 1) {
                deployFleet(cooID);
            }
        }
    }
    private boolean deployFleet(int cooID){
        Scanner scan = new Scanner(System.in);
        ChainManagementController cmc = new ChainManagementController();

        System.out.println("Enter the gas station id");
        int gasStationID = scan.nextInt();

        System.out.println("Enter the item id that is being restocked");
        int itemID = scan.nextInt();

        try {
            cmc.deployFleet(new Inventory(gasStationID, itemID));
        } catch ()

//        TransactionController tc = new TransactionController();
//        boolean complete = tc.createTransaction(itemId, gasStationID, quantity, d);
//        if(complete){
//            System.out.println("Transaction recorded!\n\n");
//        }
//        else{
//            System.out.println("Transaction failed to be recorded.");
//        }
//
//        return complete;
        return false;
    }

}
