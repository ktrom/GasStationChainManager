package UI;

import Commands.CommandExecutor;

public class COOUI {

    /**
     * Process the use cases associated with the COO.
     *
     * @param cooID employee ID of the COO authenticated
     */
    public void handleCOO(int cooID) {
        System.out.println("Logged in as COO\n");

        CommandExecutor commandExecutor = new CommandExecutor(cooID);
        commandExecutor.executeCommands();
    }
}
