package de.dhbw.softwareengineering.kontaktpilot.plugins.persistence.contact;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findByCategory(Category category);
}
