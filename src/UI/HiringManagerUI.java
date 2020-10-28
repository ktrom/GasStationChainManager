package UI;

import Commands.CommandExecutor;
import Controllers.HiringManagerController;
import GasStation.Employee;
import GasStation.EmployeeDepartment;
import HelperClasses.HelperFunctions;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;
/**-
 * UI used by an employee with the position Hiring Manager manage employees
 * Currently the only operation is hiring employees
 */
public class HiringManagerUI {
    /**
     * All available operations a hiring manager can perform
     * Manager types number corresponding to the operation they wish to perform
     * Manager can perfor operations until they quit by typing -1
     * @param managerId
     * @throws SQLException
     */
    public void handleHiringManager(int managerId) throws SQLException {
        // Check desired input against available operations
        boolean validInput = false;
        System.out.println("Logged in as Hiring Manager\n");

        CommandExecutor commandExecutor = new CommandExecutor(managerId);
        commandExecutor.executeCommands();
//        while(!validInput){
//            System.out.print("Select an option or enter -1 to quit: ");
//            int input = s.nextInt(); // user input integer
//            if(input == -1){
//                return;
//            }
//            if(input == 0){
//                hireEmployee(s, managerId);
//                validInput = true;
//            } else {
//                // input does not match any operation
//                System.out.println("Sorry, that operation is not available. Select from the list of available options.");
//            }
//        }
    }


    }

