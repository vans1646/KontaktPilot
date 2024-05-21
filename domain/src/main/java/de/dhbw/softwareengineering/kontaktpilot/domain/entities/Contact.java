package de.dhbw.softwareengineering.kontaktpilot.domain.entities;

import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.NoteNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Birthday;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactAddress;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactName;

import org.apache.commons.lang3.Validate;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Contact {

    @Id
    @GeneratedValue
    private UUID id;
    private ContactName name;
    private ContactAddress address;
    private String email;
    private String phoneNumber;
    private Category category;
    private Birthday birthday;

    @OneToMany
    private List<Note> notes;

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
        this.birthday = birthday;
    }

    public Contact() {
        this.name = new ContactName();
        this.address = new ContactAddress();
        this.email = "";
        this.phoneNumber = "";
        this.category = new Category();
        this.birthday = new Birthday();
        this.notes = List.of();
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setName(ContactName name) {
        this.name = name;
    }

    public void setAddress(ContactAddress address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public void removeNote(Note note) {
        this.notes.remove(note);
    }

    public Note getNoteById(UUID noteId) throws NoteNotFoundException {
        for (Note note : notes) {
            if (note.getId().equals(noteId)) {
                return note;
            }
        }
        throw new NoteNotFoundException("Note with id " + noteId + " not found in contact with id " + this.getId());
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
        return Objects.hash(name, address, email, phoneNumber, birthday);
    }
}
