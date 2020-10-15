package HelperClasses;

import Interfaces.Model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class HelperFunctions {
    public static Date[] getDates() {
        Date start = null, end = null;
        Scanner s = new Scanner(System.in);
        // get start date from user
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Please enter start date you wish to see revenue from (yyyy-mm-dd: ");
            String dateString = s.next();
            String[] date = dateString.split("-");
            if (date.length == 3 && Integer.parseInt(date[0]) > 999 && Integer.parseInt(date[0]) < 10000 && Integer.parseInt(date[1]) > 0 && Integer.parseInt(date[1]) < 13 && Integer.parseInt(date[2]) > 0 && Integer.parseInt(date[2]) < 32) {
                start = Date.valueOf(dateString);
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
                end = Date.valueOf(dateString);
                validDate = true;
            } else {
                System.out.println("Sorry, that's an invalid date. Type it as yyyy-mm-dd");
            }
        }

        return new Date[]{start, end};
    }
}
