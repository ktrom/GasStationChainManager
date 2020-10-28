package UI;

import Commands.Command;
import Commands.CommandExecutor;
import Controllers.ChainManagementController;
import Controllers.TransactionController;
import GasStation.Employee;
import GasStation.Inventory;
import HelperClasses.HelperFunctions;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class COOUI {

    public void handleCOO(int cooID) {
        System.out.println("Logged in as COO\n");

        CommandExecutor commandExecutor = new CommandExecutor(cooID);
        commandExecutor.executeCommands();
    }

}
