package Controllers;

import GasStation.Employee;
import GasStation.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TransactionController {

    public boolean createTransaction(int itemID, int gasStationID, double quantity, Date date){
        Transaction transaction = new Transaction(itemID, gasStationID, quantity, date);
        return transaction.create();
    }

}
