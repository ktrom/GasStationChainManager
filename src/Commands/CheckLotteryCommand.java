package Commands;

import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;

public class CheckLotteryCommand implements Command{
    public static final String description = "Check Lottery Ticket";
    private int employeeID;

    public CheckLotteryCommand(int employeeID){
        this.employeeID = employeeID;
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
     * Checks the customes age against the current date to see if they are 21 years of age or older
     * @return true if greater than 21 years else false
     */
    public static boolean checkAge() {
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.print("Please enter customer's date of birth (yyyy-mm-dd): ");
            String dob = s.next();
            String[] ymd = dob.split("-");
            if(ymd.length != 3 || Integer.parseInt(ymd[0]) < 1000 || Integer.parseInt(ymd[1]) < 1 || Integer.parseInt(ymd[1]) > 12 || Integer.parseInt(ymd[2]) < 1 || Integer.parseInt(ymd[2]) > 31){
                System.out.print("Invalid dob, try again");
            } else {
                LocalDate d = LocalDate.now();
                int day = d.getDayOfMonth();
                int month = d.getMonthValue();
                int year = d.getYear();
                if(year - Integer.parseInt(ymd[0])>21){
                    return true;
                } else if (year - Integer.parseInt(ymd[0])==21 && month - Integer.parseInt(ymd[1]) >= 0 && day - Integer.parseInt(ymd[2]) >= 0){
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * Returns if and how much a customer won on a lottery ticket
     */
    private void checkLottery(){
        boolean ofAge = checkAge();
        if(ofAge){
            int winnings = calculateWinnings();
            if(winnings > 0){
                if (winnings > 0){
                    System.out.println("Winning ticket! Customer won $" + winnings);
                }
            } else {
                System.out.println("Sorry, not a winner");
            }
        } else {
            System.out.println("Customer not 21, do not check ticket");
        }
    }

    @Override
    public void execute() {
        checkLottery();
    }

    @Override
    public String description() {
        return description;
    }
}
