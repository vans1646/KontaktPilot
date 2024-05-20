package de.dhbw.softwareengineering.kontaktpilot.domain.repositories;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Note;

import java.util.UUID;

public interface NoteBridgeRepository {
    void save(Note note);

    void delete(Note note);
}
