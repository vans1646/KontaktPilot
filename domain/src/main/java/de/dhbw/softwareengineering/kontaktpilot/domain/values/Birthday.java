package de.dhbw.softwareengineering.kontaktpilot.domain.values;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class Birthday {
    private int day;
    private int month;
    private int year;

    public Birthday() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    public Birthday(int day, int month, int year) {
        this.day = day;
        this.month = month;
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
}
