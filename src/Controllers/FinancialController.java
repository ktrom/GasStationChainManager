package Controllers;

import GasStation.Employee;
import GasStation.GasStation;
import GasStation.Utilities;

import java.sql.*;

import java.util.ArrayList;
import java.util.Scanner;

public class FinancialController {

    Date start;
    Date end;

    /**
     * Lists the operations the hiring manager has to choose from
     */
    private void printOperations() {
        System.out.println("\n");
        // For each operation type the next number: operation
        System.out.println("Operations: ");
        System.out.println("1. Calculate Revenue for Single Station");
        System.out.println("2. Calculate Revenue for station chain");
    }

    /**
     * All available operations an employee can perform
     * User types number corresponding to the operation they wish to perform
     * User can perform operations until they quit by typing -1
     * @throws SQLException
     */
    public void financialOptions() throws SQLException {
        System.out.println("\n\n");
        Scanner s = new Scanner(System.in);
        // Check desired input against available operations
        boolean exit = false;
        while (!exit) {
            printOperations();
            System.out.print("Type number of operation you wish to perform or -1 to Quit: ");


            int input = s.nextInt(); // user input integer
            if (input == 1) {
                System.out.println("Which gas station? (provide ID)");
                int stationID = s.nextInt();
                GasStation g = new GasStation(stationID);
                g.pull();
                System.out.println("Total Revenue at gas station located at " + g.getLocation() + " is: $" + calculateStationRev(stationID));
            } else if(input == 2) {
                System.out.println("Total Revenue of chain is: $" + calculateChainRev());
            } else if(input == -1){
                System.out.println("Exiting Financial Operations");
                exit = true;
            } else {
                // input does not match any operation
                System.out.println("Sorry, that operation is not available. Select from the list of available options.");
            }
        }
    }

    private double calculateChainRev() {
        getDates();
        double rev = 0;

        try {
            rev += getAllTransactionRevenue();
        } catch (SQLException throwables) {
            System.out.println("Error calculating transaction revenue");
            throwables.printStackTrace();
        }

        try {
            rev += getEmployeeDeductionRevenue();
        } catch (SQLException throwables) {
            System.out.println("Error calculating salary revenue");
            throwables.printStackTrace();
        }

        return rev;
    }

    private double calculateStationRev(int stationID) {
        getDates();
        double rev = 0;

        try {
            rev += getStationTransactionRevenue(stationID);
        } catch (SQLException throwables) {
            System.out.println("Error calculating transaction revenue");
            throwables.printStackTrace();
        }

        try {
            rev += getEmployeeDeductionRevenue(stationID);
        } catch (SQLException throwables) {
            System.out.println("Error calculating salary revenue");
            throwables.printStackTrace();
        }

        return rev;
    }

    private void getDates() {
        Scanner s = new Scanner(System.in);
        // get start date from user
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Please enter start date you wish to see revenue from (yyyy-mm-dd: ");
            String dateString = s.next();
            String[] date = dateString.split("-");
            if (date.length == 3 && Integer.parseInt(date[0]) > 999 && Integer.parseInt(date[0]) < 10000 && Integer.parseInt(date[1]) > 0 && Integer.parseInt(date[1]) < 13 && Integer.parseInt(date[2]) > 0 && Integer.parseInt(date[2]) < 32) {
                this.start = Date.valueOf(dateString);
                validDate = true;
            } else {
                System.out.println("Sorry, that's an invalid date. Type it as yyyy-mm-dd");
            }
        }

        // get end date from user
        validDate = false;
        while (!validDate) {
            System.out.print("Please enter end date you wish to see revenue from (yyyy-mm-dd: ");
            String dateString = s.next();
            String[] date = dateString.split("-");
            if (date.length == 3 && Integer.parseInt(date[0]) > 999 && Integer.parseInt(date[0]) < 10000 && Integer.parseInt(date[1]) > 0 && Integer.parseInt(date[1]) < 13 && Integer.parseInt(date[2]) > 0 && Integer.parseInt(date[2]) < 32) {
                this.end = Date.valueOf(dateString);
                validDate = true;
            } else {
                System.out.println("Sorry, that's an invalid date. Type it as yyyy-mm-dd");
            }
        }
    }

    private double getEmployeeDeductionRevenue() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT Salary FROM hsnkwamy_GasStation.Employee";
        PreparedStatement ps = conn.prepareStatement(stationQuery);

        // Execute query
        ResultSet rs = ps.executeQuery();

        double salaryRevenue = 0;
        // Set attributes for this GasStation

        while (rs.next()) {
            double extrapolatedDailyPay = (rs.getDouble("Salary"))/365;
            double millisecondsElapsed =  end.getTime() - start.getTime();
            double daysElapsed = millisecondsElapsed/(8.64*Math.pow(10, 7));
            salaryRevenue -= extrapolatedDailyPay * daysElapsed;
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return salaryRevenue;
    }


    private double getEmployeeDeductionRevenue(int gasStationID) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT Salary FROM hsnkwamy_GasStation.Employee WHERE GasStationID = ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, gasStationID);

        // Execute query
        ResultSet rs = ps.executeQuery();

        double salaryRevenue = 0;
        // Set attributes for this GasStation

        while (rs.next()) {
            double extrapolatedDailyPay = (rs.getDouble("Salary"))/365;
            double millisecondsElapsed =  end.getTime() - start.getTime();
            double daysElapsed = millisecondsElapsed/(8.64*Math.pow(10, 7));
            salaryRevenue -= extrapolatedDailyPay * daysElapsed;
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return salaryRevenue;
    }

    private double getStationTransactionRevenue(int gasStationID) throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT Quantity, Price, SupplierPrice FROM hsnkwamy_GasStation.Transaction, hsnkwamy_GasStation.Item WHERE GasStationID = ? AND Transaction.ItemID = Item.ItemID " +
                "AND Transaction.DateSold BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setInt(1, gasStationID);
        ps.setDate(2, start);
        ps.setDate(3, end);

        // Execute query
        ResultSet rs = ps.executeQuery();

        double transactionRevenue = 0;
        // Set attributes for this GasStation

        while (rs.next()) {
            double quantity = (rs.getDouble("Quantity"));
            double price = (rs.getDouble("Price"));
            double supplierPrice = (rs.getDouble("SupplierPrice"));
            transactionRevenue += quantity * (price - supplierPrice);
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return transactionRevenue;
    }

    private double getAllTransactionRevenue() throws SQLException {
        // Get database connection
        Connection conn = Utilities.getConnection();

        // Build query
        String stationQuery = "SELECT Quantity, Price, SupplierPrice FROM hsnkwamy_GasStation.Transaction, hsnkwamy_GasStation.Item WHERE Transaction.ItemID = Item.ItemID " +
                "AND Transaction.DateSold BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(stationQuery);
        ps.setDate(1, start);
        ps.setDate(2, end);

        // Execute query
        ResultSet rs = ps.executeQuery();

        double transactionRevenue = 0;
        // Set attributes for this GasStation

        while (rs.next()) {
            double quantity = (rs.getDouble("Quantity"));
            double price = (rs.getDouble("Price"));
            double supplierPrice = (rs.getDouble("SupplierPrice"));
            transactionRevenue += quantity * (price - supplierPrice);
        }

        // Close all opened streams
        rs.close();
        ps.close();
        conn.close();

        return transactionRevenue;
    }

}
