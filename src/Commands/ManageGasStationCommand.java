package Commands;

import Controllers.ChainManagementController;
import GasStation.GasStation;

import java.sql.*;
import java.util.Scanner;

public class ManageGasStationCommand implements Command {
    private final static String description = "Manage Gas Station";
    private int cooID;

    public ManageGasStationCommand(int cooID) {
        this.cooID = cooID;
    }

    private boolean manageStation() {
        // Setup input and controller
        Scanner scan = new Scanner(System.in);
        ChainManagementController cmc = new ChainManagementController();

        // Get the existing gas station ID
        System.out.println("\nEnter the ID of the gas station to manage:");
        int gasStationId = scan.nextInt();

        System.out.println("\nAll changes are optional - press enter to skip an update.");

        // Get all input required for gas station management
        scan.nextLine();
        System.out.println("\nEnter the updated address of the gas station:");
        String location = scan.nextLine();

        System.out.println("\nEnter the gas station name:");
        String name = scan.nextLine();

        System.out.println("\nEnter the phone number of the gas station:");
        String phoneNumber = scan.nextLine();

        System.out.println("\nEnter the URL of the design plans:");
        String photo = scan.nextLine();

        System.out.println("\nEnter any notes on the gas station:");
        String notes = scan.nextLine();

        System.out.println();

        try {
            // Construct given gas station
            GasStation toUpdate = new GasStation(gasStationId);
            toUpdate.pull();

            // Set the updated fields
            toUpdate.setLocation(location);
            toUpdate.setName(name);
            toUpdate.setPhoneNumber(phoneNumber);
            toUpdate.setPhoto(photo);
            toUpdate.setNotes(notes);

            boolean success = cmc.manageGasStation(toUpdate);
            if (!success) {
                // Failed to update the gas station
                System.out.println("The gas station could not be built. Please try again later.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("System error - please try again.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Handle the different error scenarios here
            System.out.println(e.getMessage() + "\n");
            return false;
        }

        System.out.println("Gas station successfully updated!\n\n");
        return true;
    }

    @Override
    public void execute() {
        manageStation();
    }

    @Override
    public String description() {
        return description;
    }
}
