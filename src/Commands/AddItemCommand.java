package Commands;

import Controllers.ChainManagementController;
import GasStation.Item;

import java.sql.SQLException;
import java.util.Scanner;

public class AddItemCommand implements Command {
    private final static String description = "Add Item";
    private int cooID;

    public AddItemCommand(int cooID) {
        this.cooID = cooID;
    }

    private boolean addItem() {
        Scanner scan = new Scanner(System.in);
        ChainManagementController cmc = new ChainManagementController();

        System.out.println("\nEnter the item name:");
        String name = scan.nextLine();

        System.out.println("\nEnter the item sale price:");
        double salePrice = scan.nextDouble();

        System.out.println("\nEnter the item purchase price from supplier:");
        double supplierPrice = scan.nextDouble();

        System.out.println("\nEnter a link to a photo of the item:");
        scan.nextLine();
        String photoURL = scan.nextLine();

        System.out.println("\nEnter any notes for the item:");
        String notes = scan.nextLine();

        try {
            boolean success = cmc.addItem(new Item(name, salePrice, supplierPrice, photoURL, notes));
            if (!success) {
                System.out.println("Item could not be added.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("\nSystem error - please try again.");
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("duplicate")) {
                System.out.println("\nItem is already in system.\n");
            } else {
                System.out.println("\nItem prices must be more than $0.\n");
            }
            return false;
        }

        System.out.println("\nItem successfully added!\n");
        return true;
    }

    @Override
    public void execute() {
        addItem();
    }

    @Override
    public String description() {
        return description;
    }
}
