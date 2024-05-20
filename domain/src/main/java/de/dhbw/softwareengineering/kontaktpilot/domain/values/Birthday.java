package de.dhbw.softwareengineering.kontaktpilot.domain.values;

import jakarta.persistence.Embeddable;


@Embeddable
public class Birthday {

    private final int birthday;
    private final int birthmonth;
    private final int birthyear;

    public Birthday() {
        this.birthday = 0;
        this.birthmonth = 0;
        this.birthyear = 0;
    }

    public Birthday(int day, int month, int year) {
        this.birthday = day;
        this.birthmonth = month;
        this.birthyear = year;
    }

    public int getBirthday() {
        return birthday;
    }

    public int getBirthmonth() {
        return birthmonth;
    }

    public int getBirthyear() {
        return birthyear;
    }
}
