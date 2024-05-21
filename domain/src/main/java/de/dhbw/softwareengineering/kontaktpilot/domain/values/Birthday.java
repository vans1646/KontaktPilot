package de.dhbw.softwareengineering.kontaktpilot.domain.values;

import jakarta.persistence.Embeddable;

import java.util.Objects;


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
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be between 1 and 31");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (year < 1900 || year > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year must be between 1900 and current year");
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Birthday birthday1 = (Birthday) o;
        return birthday == birthday1.birthday && birthmonth == birthday1.birthmonth && birthyear == birthday1.birthyear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, birthmonth, birthyear);
    }
}
