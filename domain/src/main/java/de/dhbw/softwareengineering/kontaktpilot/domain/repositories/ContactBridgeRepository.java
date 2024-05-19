package de.dhbw.softwareengineering.kontaktpilot.domain.repositories;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;

import java.util.List;
import java.util.UUID;

public interface ContactBridgeRepository {
    void save(Contact contact);

    List<Contact> getAllContacts();

    Contact getContact(UUID uuid);

    void deleteContact(UUID uuid);

    List<Contact> getContactsByCategory(Category category);

    List<Category> getAllCategories();
}
