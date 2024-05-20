package de.dhbw.softwareengineering.kontaktpilot.plugins.persistence.note;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringNoteRepository extends JpaRepository<Note, UUID> {
}
