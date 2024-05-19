package de.dhbw.softwareengineering.kontaktpilot.application.services1;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.repositories.ContactBridgeRepository;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
        return contactBridgeRepository.getContact(uuid);
    }

    public boolean addContact(Contact contact) {
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
        contactBridgeRepository.deleteContact(uuid);
    }

    public List<Contact> getContactsByCategory(Category category) {
        return contactBridgeRepository.getContactsByCategory(category);
    }

    public List<Category> getAllCategories() {
        List<Category> allCategories = contactBridgeRepository.getAllCategories();
        List<Category> filteredCategories = allCategories.stream().distinct().collect(Collectors.toList());
        return new ArrayList<>(filteredCategories);
    }
}
