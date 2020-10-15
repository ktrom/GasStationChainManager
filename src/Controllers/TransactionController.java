package Controllers;

import GasStation.Employee;
import GasStation.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Handles database operations for the Transaction Class
 */
public class TransactionController {

    /**
     * Creates and saves a Transaction to the database
     * @param itemID ID of item being sold
     * @param gasStationID ID of gas station where item is sold
     * @param quantity Number of item being sold
     * @param date Day item is sold
     * @return true if succesful creation & saving to the database, false otherwise
     */
    public boolean createTransaction(int itemID, int gasStationID, double quantity, Date date){
        Transaction transaction = new Transaction(itemID, gasStationID, quantity, date);
        return transaction.create();
    }

}
