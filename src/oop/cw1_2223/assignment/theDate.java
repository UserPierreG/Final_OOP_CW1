package oop.cw1_2223.assignment;

public class theDate {

    private final int year;
    private final int month;
    private final int day;
    private final String method;

    public theDate(int year, int month, int day, String method) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.method = method;
    }

    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }
    public String getMethod() {
        return method;
    }
}
