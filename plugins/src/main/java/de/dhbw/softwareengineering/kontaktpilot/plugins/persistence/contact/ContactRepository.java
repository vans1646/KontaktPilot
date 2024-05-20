package de.dhbw.softwareengineering.kontaktpilot.plugins.persistence.contact;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.ContactNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.repositories.ContactBridgeRepository;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ContactRepository implements ContactBridgeRepository {

    private final SpringContactRepository springContactRepository;

    @Autowired
    public ContactRepository(SpringContactRepository springContactRepository) {
        this.springContactRepository = springContactRepository;
    }

    @Override
    public void save(Contact contact){
        this.springContactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return this.springContactRepository.findAll();
    }

    @Override
    public Contact getContact(UUID uuid) {
        return this.springContactRepository.findById(uuid).orElse(null);
    }

    @Override
    public void deleteContact(UUID uuid) {
        if (!this.springContactRepository.existsById(uuid)) {
            throw new ContactNotFoundException("Contact with UUID " + uuid + " does not exist");
        }
        this.springContactRepository.deleteById(uuid);
    }

    @Override
    public List<Contact> getContactsByCategory(Category category) {
        return this.springContactRepository.findByCategory(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.springContactRepository.findAll().stream().map(Contact::getCategory).toList();
    }
}
