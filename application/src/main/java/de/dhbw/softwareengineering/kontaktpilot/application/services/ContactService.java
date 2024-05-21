package de.dhbw.softwareengineering.kontaktpilot.application.services;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.CategoryNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.ContactNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.repositories.ContactBridgeRepository;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Birthday;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactAddress;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactBridgeRepository contactBridgeRepository;

    @Autowired
    public ContactService(ContactBridgeRepository contactBridgeRepository) {
        this.contactBridgeRepository = contactBridgeRepository;
    }

    public List<Contact> getAllContacts() {
        return contactBridgeRepository.getAllContacts();
    }

    public Contact getContact(UUID uuid) {
        try {
            return contactBridgeRepository.getContact(uuid);
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public boolean addContact(Contact contact) {
        if (contact.getName() == null || contact.getName().getFirstName().isBlank() || contact.getName().getLastName().isBlank()) {
            throw new IllegalArgumentException("Contact name must not be empty");
        } else if (contact.getCategory() == null || contact.getCategory().getName().isBlank()) {
            throw new IllegalArgumentException("Contact category must not be empty");
        } else if (contact.getPhoneNumber() == null || contact.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Contact phone number must not be empty");
        } else if (contact.getEmail() == null || contact.getEmail().isBlank()) {
            throw new IllegalArgumentException("Contact email must not be empty");
        } else if (contact.getAddress() == null || //
                contact.getAddress().getStreet().isBlank() || //
                contact.getAddress().getCity().isBlank() || //
                contact.getAddress().getZipCode().isBlank() || //
                contact.getAddress().getHouseNumber().isBlank()) {
            throw new IllegalArgumentException("Contact address must not be empty");
        } else if (contact.getBirthday() == null) {
            throw new IllegalArgumentException("Contact birthday must not be empty");
        }
        List<Contact> allContacts = contactBridgeRepository.getAllContacts();
        for (Contact existingContact : allContacts) {
            if (existingContact.equals(contact)) {
                return false;
            }
        }
        contactBridgeRepository.save(contact);
        return true;
    }

    public void deleteContact(UUID uuid) {
        try {
            contactBridgeRepository.deleteContact(uuid);
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public List<Contact> getContactsByCategory(Category category) {
        try {
            return contactBridgeRepository.getContactsByCategory(category);
        } catch (CategoryNotFoundException e) {
            throw e;
        }
    }

    public List<Category> getAllCategories() {
        List<Category> allCategories = contactBridgeRepository.getAllCategories();
        List<Category> filteredCategories = allCategories.stream().distinct().collect(Collectors.toList());
        return new ArrayList<>(filteredCategories);
    }

    public Contact searchContact(ContactName contactName) {
        try {
            List<Contact> allContacts = contactBridgeRepository.getAllContacts();
            for (Contact contact : allContacts) {
                if (contact.getName().equals(contactName)) {
                    return contact;
                }
            }
        } catch (ContactNotFoundException e) {
            throw e;
        }
        return null;
    }

    public Contact updateContactName(UUID uuid, ContactName name) {
        if (name == null || name.getFirstName().isBlank() || name.getLastName().isBlank()) {
            throw new IllegalArgumentException("Contact name must not be empty");
        }
        try {
            Contact contact = contactBridgeRepository.getContact(uuid);
            contact.setName(name);
            contactBridgeRepository.save(contact);
            return contact;
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public Contact updateContactAddress(UUID uuid, ContactAddress address) {
        if (address == null || //
                address.getStreet().isBlank() || //
                address.getCity().isBlank() || //
                address.getZipCode().isBlank() || //
                address.getHouseNumber().isBlank()) {
            throw new IllegalArgumentException("Contact address must not be empty");
        }
        try {
            Contact contact = contactBridgeRepository.getContact(uuid);
            contact.setAddress(address);
            contactBridgeRepository.save(contact);
            return contact;
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public Contact updateContactEmail(UUID uuid, String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Contact email must not be empty");
        }
        try {
            Contact contact = contactBridgeRepository.getContact(uuid);
            contact.setEmail(email);
            contactBridgeRepository.save(contact);
            return contact;
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public Contact updateContactPhoneNumber(UUID uuid, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Contact phone number must not be empty");
        }
        try {
            Contact contact = contactBridgeRepository.getContact(uuid);
            contact.setPhoneNumber(phoneNumber);
            contactBridgeRepository.save(contact);
            return contact;
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }


    public Contact updateContactCategory(UUID uuid, Category category) {
        if (category == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Contact category must not be empty");
        }
        try {
            Contact contact = contactBridgeRepository.getContact(uuid);
            contact.setCategory(category);
            contactBridgeRepository.save(contact);
            return contact;
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public Contact updateContactBirthday(UUID uuid, Birthday birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("Contact birthday must not be empty");
        }
        try {
            Contact contact = contactBridgeRepository.getContact(uuid);
            contact.setBirthday(birthday);
            contactBridgeRepository.save(contact);
            return contact;
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }
}
