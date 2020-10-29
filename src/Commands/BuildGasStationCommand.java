package Commands;

import Controllers.ChainManagementController;
import GasStation.GasStation;

import java.sql.*;
import java.util.Scanner;

public class BuildGasStationCommand implements Command {
    private final static String description = "Build Gas Station";
    private int cooID;

    public BuildGasStationCommand(int cooID) {
        this.cooID = cooID;
    }

    private boolean buildGasStation() {
        Scanner scan = new Scanner(System.in);
        ChainManagementController cmc = new ChainManagementController();

        System.out.println("\nEnter the address of the gas station to build:");
        String location = scan.nextLine();

        System.out.println("\nEnter the gas station name:");
        String name = scan.nextLine();

        System.out.println("\nEnter the phone number of the gas station:");
        String phoneNumber = scan.nextLine();

        System.out.println("\nEnter the URL of the design plans:");
        String photo = scan.nextLine();

        System.out.println("\nEnter the total estimated construction cost (rounded):");
        long constructionCost = scan.nextLong();

        System.out.println("\nEnter any notes on the gas station:");
        scan.nextLine();
        String notes = scan.nextLine();

        System.out.println();

        try {
            boolean success = cmc.buildGasStation(new GasStation(location, name, phoneNumber, photo, constructionCost, notes));
            if (!success) {
                // Failed to create the database record
                System.out.println("The gas station could not be built. Please try again later.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("System error - please try again.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Handle the different error scenarios here
            if(e.getMessage().equals("location")) {
                System.out.println("Chain already owns a gas station at the provided address.\n");
            } else {
                System.out.println("The phone number entered is already in use.\n");
            }

            return false;
        }

        System.out.println("Gas station successfully built!\n\n");
        return true;
    }

    @Override
    public void execute() {
        buildGasStation();
    }

    @Override
    public String description() {
        return description;
    }
}
