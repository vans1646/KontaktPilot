package de.dhbw.softwareengineering.kontaktpilot.plugins.rest;

import de.dhbw.softwareengineering.kontaktpilot.application.services.NoteService;
import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Note;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.NoteNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/api/contacts/notes")
@Tag(name = "Notes", description = "Operations for managing the notes of a contact")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getDefault() {
        return "Hello World on Notes!";
    }

    /*
        Endpoints /notes/*
        Interact with notes
    */

    @PostMapping(path = "/add/{contactId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note added"),
            @ApiResponse(responseCode = "400", description = "Note could not be added")
    })
    public ResponseEntity<UUID> addNote(@PathVariable UUID contactId, @RequestBody Note note) {
        try {
            return ResponseEntity.ok(noteService.addNoteToContact(note, contactId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{contactId}/{noteId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note found"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<Note> getNoteById(@PathVariable UUID contactId, @PathVariable UUID noteId) {
        try {
            return ResponseEntity.ok(noteService.getNoteById(contactId, noteId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/all/{contactId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes found"),
            @ApiResponse(responseCode = "404", description = "No notes found")
    })
    public ResponseEntity<List<Note>> getAllNotesByContactId(@PathVariable UUID contactId) {
        try {
            return ResponseEntity.ok(noteService.getAllNotesByContactId(contactId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{contactId}/{noteId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note deleted"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<Void> deleteNote(@PathVariable UUID contactId, @PathVariable UUID noteId) {
        try {
            noteService.deleteNoteById(contactId, noteId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/update/title/{contactId}/{noteId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<Note> updateNoteTitle(@PathVariable UUID contactId, @PathVariable UUID noteId, @RequestBody String title) {
        try {
            return ResponseEntity.ok(noteService.updateNoteTitle(contactId, noteId, title));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/update/content/{contactId}/{noteId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<Note> updateNoteContent(@PathVariable UUID contactId, @PathVariable UUID noteId, @RequestBody String content) {
        try {
            return ResponseEntity.ok(noteService.updateNoteContent(contactId, noteId, content));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
