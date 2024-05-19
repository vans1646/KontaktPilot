package de.dhbw.softwareengineering.kontaktpilot.domain.values;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ContactAddress {
    private final String street;
    private final String houseNumber;
    private final String zipCode;
    private final String city;

    public ContactAddress(String street, String houseNumber, String zipCode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public ContactAddress() {
        this.street = "";
        this.houseNumber = "";
        this.zipCode = "";
        this.city = "";
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactAddress that = (ContactAddress) o;
        return Objects.equals(street, that.street) && Objects.equals(houseNumber, that.houseNumber) && Objects.equals(zipCode, that.zipCode) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, zipCode, city);
    }
}
