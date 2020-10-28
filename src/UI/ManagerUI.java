package UI;

import Commands.CommandExecutor;
import Controllers.EmployeeController;
import Controllers.GasStationController;
import Controllers.ScheduleController;
import Controllers.TaskController;
import GasStation.Employee;
import GasStation.EmployeePosition;
import GasStation.Task;
import HelperClasses.HelperFunctions;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * User Interface class for a manager
 */
public class ManagerUI {

    /**
     * Handles display and input for manager options
     */
    public void handleManager(int managerID) {
        System.out.println("Logged in as Manager\n");

        CommandExecutor commandExecutor = new CommandExecutor(managerID);
        commandExecutor.executeCommands();
    }
}







