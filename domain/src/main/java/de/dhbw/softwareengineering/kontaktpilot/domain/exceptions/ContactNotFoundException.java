package de.dhbw.softwareengineering.kontaktpilot.domain.exceptions;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
