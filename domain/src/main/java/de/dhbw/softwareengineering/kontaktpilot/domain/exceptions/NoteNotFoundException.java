package de.dhbw.softwareengineering.kontaktpilot.domain.exceptions;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(String message) {
        super(message);
    }

}
