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

    public void makeTransaction(int attendantID){
        Scanner scan = new Scanner(System.in);
        Employee currentAttendant = new Employee(attendantID);

        try {
            currentAttendant.pull();
        } catch (SQLException throwables) {
            System.out.println("Could not find attendant ID");
            throwables.printStackTrace();
        }

        int gasStationID = currentAttendant.getGasStationID();

        System.out.println("Enter the item id");
        int itemId = scan.nextInt();

        System.out.println("Enter the quantity that is being purchased");
        double quantity = scan.nextDouble();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime todaysDate = LocalDateTime.now();
        String k = todaysDate.toString();
        Date d = Date.valueOf(todaysDate.toString().substring(0,10));


        Transaction transaction = new Transaction(itemId, gasStationID, quantity, d);

        try {
            transaction.create();
        } catch (SQLException throwables) {
            System.out.println("Transaction could not be created");
            throwables.printStackTrace();
        }
    }


}
