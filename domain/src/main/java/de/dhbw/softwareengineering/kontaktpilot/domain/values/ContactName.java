package de.dhbw.softwareengineering.kontaktpilot.domain.values;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ContactName {
    private final String firstName;
    private final String lastName;

    public ContactName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ContactName() {
        this.firstName = "";
        this.lastName = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactName that = (ContactName) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
