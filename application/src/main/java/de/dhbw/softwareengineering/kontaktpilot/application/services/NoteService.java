package de.dhbw.softwareengineering.kontaktpilot.application.services;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Note;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.ContactNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.NoteNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.repositories.ContactBridgeRepository;
import de.dhbw.softwareengineering.kontaktpilot.domain.repositories.NoteBridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteBridgeRepository noteBridgeRepository;
    private final ContactBridgeRepository contactBridgeRepository;

    @Autowired
    public NoteService(NoteBridgeRepository noteBridgeRepository, ContactBridgeRepository contactBridgeRepository) {
        this.noteBridgeRepository = noteBridgeRepository;
        this.contactBridgeRepository = contactBridgeRepository;
    }

    public UUID addNoteToContact(Note note, UUID contactId) {
        if (note.getTitle() == null || note.getTitle().isBlank()) {
            throw new IllegalArgumentException("Note title must not be empty");
        } else if (note.getContent() == null || note.getContent().isBlank()) {
            throw new IllegalArgumentException("Note content must not be empty");
        }
        try {
            Contact contact = contactBridgeRepository.getContact(contactId);
            contact.addNote(note);
            noteBridgeRepository.save(note);
            contactBridgeRepository.save(contact);
            return note.getId();
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public Note getNoteById(UUID contactId, UUID noteId) {
        try {
            Contact contact = contactBridgeRepository.getContact(contactId);
            return contact.getNoteById(noteId);
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public List<Note> getAllNotesByContactId(UUID contactId) {
        try {
            Contact contact = contactBridgeRepository.getContact(contactId);
            return contact.getNotes();
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public void deleteNoteById(UUID contactId, UUID noteId) {
        try {
            Contact contact = contactBridgeRepository.getContact(contactId);
            Note note = contact.getNoteById(noteId);
            contact.removeNote(note);
            noteBridgeRepository.delete(note);
            contactBridgeRepository.save(contact);
        } catch (ContactNotFoundException e) {
            throw e;
        }
    }

    public Note updateNoteTitle(UUID contactId, UUID noteId, String title) {
        try {
            Contact contact = contactBridgeRepository.getContact(contactId);
            Note note = contact.getNoteById(noteId);
            note.setTitle(title);
            noteBridgeRepository.save(note);
            return note;
        } catch (ContactNotFoundException e) {
            throw e;
        } catch (NoteNotFoundException e) {
            throw e;
        }
    }

    public Note updateNoteContent(UUID contactId, UUID noteId, String content) {
        try {
            Contact contact = contactBridgeRepository.getContact(contactId);
            Note note = contact.getNoteById(noteId);
            note.setContent(content);
            noteBridgeRepository.save(note);
            return note;
        } catch (ContactNotFoundException e) {
            throw e;
        } catch (NoteNotFoundException e) {
            throw e;
        }
    }
}
