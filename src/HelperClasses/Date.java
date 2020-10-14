package HelperClasses;

import java.util.Objects;

public class Date {
    private int day;
    private int month;
    private int year;

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }


    public Date(int mm, int dd, int yyyy){
        month = mm;
        day = dd;
        year = yyyy;
    }
}
