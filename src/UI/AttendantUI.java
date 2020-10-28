package UI;

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
        Scanner scan = new Scanner(System.in);
        int option = 1;
        System.out.println("Logged in as Attendant\n");

        while (option == 1 || option == 2) {

            System.out.println("Select an option or enter -1 to quit:");
            System.out.println("1. Make Transaction");
            System.out.println("2. View/Complete Task");
            System.out.println("3. Check Lottery Ticket");

            option = scan.nextInt();

            if (option == 1) {
                makeTransaction(attendantID);
            } else if(option == 2){
                completeTask(attendantID);
            } else if(option == 3){
                checkLottery(attendantID);
            }
        }
    }

    /**
     * Enables an Attendant to process a transaction
     * @param attendantID ID of Attendant (Actor)
     * @return true if transaction was successfully recorded, false otherwise
     */
    private boolean makeTransaction(int attendantID){
        Scanner scan = new Scanner(System.in);
        EmployeeController ec = new EmployeeController();

        int gasStationID = ec.getGasStationID(attendantID);

        System.out.println("Enter the item id:");
        int itemId = scan.nextInt();

        System.out.println("Enter the quantity that is being purchased:");
        int quantity = scan.nextInt();

        SaleController sc = new SaleController();
        int numSold;
        try {
            numSold = sc.sellItem(gasStationID, itemId, quantity);
        } catch (SQLException e) {
            System.out.println("System error.");
            return false;
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime todaysDate = LocalDateTime.now();
        String k = todaysDate.toString();
        Date d = Date.valueOf(todaysDate.toString().substring(0,10));

        TransactionController tc = new TransactionController();
        boolean complete = tc.createTransaction(itemId, gasStationID, numSold, d);
        if(complete){
            System.out.println("Transaction recorded!\n\n");
        }
        else{
            System.out.println("Transaction failed to be recorded.");
        }

        return complete;
    }

    /**
     * Allows the attendant to complete a task assigned to them
     * @param attendantID ID of the actor (attendant)
     */
    private void completeTask(int attendantID){
        Scanner scan = new Scanner(System.in);
        EmployeeController ec = new EmployeeController();
        ArrayList<Task> employeeTasks = ec.getTasks(attendantID);
        System.out.println("Enter Task # to complete or -1 to exit");
        for(int i = 0; i <  employeeTasks.size(); i++){
            System.out.println("Task "+ (i + 1) + ": " + employeeTasks.get(i).getTaskDescription());
        }
        int choice = scan.nextInt() - 1;

        if(choice > -1) {
            TaskController tc = new TaskController();
            tc.deleteTask(employeeTasks.get(choice).getTaskID());
        }
    }

    /**
     * Checks if the given number num is in the list of numbers
     * @param numbers
     * @param num
     * @return true if in list else false
     */
    private boolean inList(String[] numbers, int num){
        String newNum;
        if(num < 10){
            newNum = "0" + num;
        } else {
            newNum = Integer.toString(num);
        }
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] != null && numbers[i].equals(newNum)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets user input for 6 numbers between 1 and 69
     * @return string of customer lotto numbers
     */
    private String[] getLottoNumbers(){
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.print("Enter customers lottery numbers (## ## ## ## ## ##): ");
            String[] numbersString = s.nextLine().split(" ");
            for(int i = 0; i < numbersString.length; i++){
                if(numbersString[i].length() == 2){
                    int num1 = Integer.parseInt(numbersString[i].substring(0,1));
                    int num2 = Integer.parseInt(numbersString[i].substring(1,2));
                    if(num1 == 0 && num2 == 0|| num2 < 1 || num1 > 69 || num2 > 69){
                        System.out.println("Invalid Input");
                        break;
                    } else {
                        return numbersString;
                    }
                } else {
                    System.out.println("Invalid Input");
                }
            }
        }
    }

    /**
     * Generates 6 unique random numbers between 1 and 69
     * @return string of winning numbers
     */
    private String[] getRandomLottoNumbers(){
        Random r = new Random();
        String[] numbers = new String[6];
        for(int i = 0; i < 6; i++){
            int num = r.nextInt(69);
            if(!inList(numbers, num)){
                if(num < 10){
                    numbers[i] = "0" + num;
                } else {
                    numbers[i] = Integer.toString(num);
                }
            } else {
                i--;
            }

        }
        return numbers;
    }

    /**
     * Checks how many numbers match and adds on increasing winnings from each match
     * @return total winnings
     */
    private int calculateWinnings() {
        int winnings = 0;
        int winningCount = 0;
        String[] lottoNumbers = getLottoNumbers();
        String[] winningNumbers = getRandomLottoNumbers();
        System.out.print("Winning numbers are: ");

        for(int i = 0; i < lottoNumbers.length; i++){
            System.out.print(winningNumbers[i] + " ");
            for(int j = 0; j < winningNumbers.length; j++){
                if(lottoNumbers[i].equals(winningNumbers[j])){
                    winningCount++;
                    if(winningCount == 1){
                        winnings += 25;
                    } else if( winningCount == 2){
                        winnings += 100;
                    } else if( winningCount == 3){
                        winnings += 1000;
                    } else if( winningCount == 4){
                        winnings += 10000;
                    } else if( winningCount == 5){
                        winnings += 100000;
                    } else if( winningCount == 6){
                        winnings += 1000000;
                    }
                }
            }
        }
        System.out.println();
        return winnings;
    }

    /**
     * Returns if and how much a customer won on a lottery ticket
     * @param attendantID
     */
    private void checkLottery(int attendantID){
        int winnings = calculateWinnings();
        if(winnings > 0){
            if (winnings < 100){
                System.out.print("Winning ticket! Pay out " + winnings);
                //make transaction to deduct from revenue
            } else {
                System.out.print("Jackpot ticket! $" + winnings + " is too much, send customer to state lotto");
            }
        } else {
            System.out.print("Sorry, not a winner");
        }
    }
}
