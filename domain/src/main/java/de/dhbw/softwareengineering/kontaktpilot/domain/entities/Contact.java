package de.dhbw.softwareengineering.kontaktpilot.domain.entities;

import de.dhbw.softwareengineering.kontaktpilot.domain.values.Birthday;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactAddress;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactName;

import org.apache.commons.lang3.Validate;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="users")
public class Contact {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="contact_id")
    private UUID id;
    private ContactName name;
    private ContactAddress address;
    private String email;
    private String phoneNumber;
    private Category category;

    private Birthday birthday;

    public Contact(ContactName name, ContactAddress address, String email, String phoneNumber, Category category, Birthday birthday){
        Validate.notBlank(name.getFirstName());
        Validate.notBlank(name.getLastName());
        Validate.notBlank(address.getStreet());
        Validate.notBlank(address.getHouseNumber());
        Validate.notBlank(address.getCity());
        Validate.notBlank(address.getZipCode());
        Validate.notBlank(email);
        Validate.notBlank(phoneNumber);
        Validate.notNull(category);
        Validate.notNull(birthday);

        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.category = category;
    }

    public Contact() {
        this.name = new ContactName();
        this.address = new ContactAddress();
        this.email = "";
        this.phoneNumber = "";
        this.category = new Category();
        this.birthday = new Birthday();
    }

    public UUID getId() {
        return id;
    }

    public ContactName getName() {
        return name;
    }

    public ContactAddress getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Category getCategory() {
        return category;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && //
                Objects.equals(address, contact.address) && //
                Objects.equals(email, contact.email) && //
                Objects.equals(phoneNumber, contact.phoneNumber) && //
                Objects.equals(birthday, contact.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, email, phoneNumber);
    }
}
