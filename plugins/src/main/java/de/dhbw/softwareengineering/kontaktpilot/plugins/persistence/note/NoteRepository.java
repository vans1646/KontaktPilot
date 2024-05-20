package de.dhbw.softwareengineering.kontaktpilot.plugins.persistence.note;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Note;
import de.dhbw.softwareengineering.kontaktpilot.domain.repositories.NoteBridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class NoteRepository implements NoteBridgeRepository {

    private final SpringNoteRepository springNoteRepository;

    @Autowired
    public NoteRepository(SpringNoteRepository springNoteRepository) {
        this.springNoteRepository = springNoteRepository;
    }

    @Override
    public void save(Note note) {
        this.springNoteRepository.save(note);
    }

    @Override
    public void delete(Note note) {
        this.springNoteRepository.delete(note);
    }
}
