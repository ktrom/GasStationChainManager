package UI;

import Commands.CommandExecutor;
import Controllers.EmployeeController;
import Controllers.SaleController;
import Controllers.TaskController;
import Controllers.TransactionController;
import GasStation.Employee;
import GasStation.Task;
import GasStation.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
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
        System.out.println("Logged in as Attendant\n");

        CommandExecutor commandExecutor = new CommandExecutor(attendantID);
        commandExecutor.executeCommands();
    }
}
