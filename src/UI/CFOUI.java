package UI;

import Commands.CommandExecutor;
import Controllers.FinancialController;
import Controllers.GasStationController;
import GasStation.GasStation;
import HelperClasses.HelperFunctions;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * User Interaction class for the CFO
 */
public class CFOUI {

    /**
     * Handles the case where the actor is a CFO
     * @param CFOID ID of CFO (Actor)
     */
    public void handleCFO(int CFOID){
        System.out.println("Logged in as CFO\n");

        CommandExecutor commandExecutor = new CommandExecutor(CFOID);
        commandExecutor.executeCommands();
    }




}
