package de.dhbw.softwareengineering.kontaktpilot.plugins.rest;

import de.dhbw.softwareengineering.kontaktpilot.application.services.ContactService;
import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.ContactNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Birthday;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactAddress;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactControllerContactsTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    void setUp() {
        contactController = new ContactController(contactService);
    }

    @Test
    @DisplayName("Test default contact endpoint")
    void testDefaultContact() {
        String expected = "Hello World";
        String actual = contactController.getDefault();
        assertEquals(expected, actual, "Default message should be 'Hello World'");
    }

    @Test
    @DisplayName("Test all contacts are empty")
    void testAllContacts_AssumeEmpty() {
        assertEquals(0, contactController.getAllContacts().size(), "All contacts should be empty");
    }

    @Test
    @DisplayName("Test all contacts has one value")
    void testAllContacts_AssumeOne() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        // Action
        when(contactService.getAllContacts()).thenReturn(Collections.singletonList(contact));

        // Test
        assertEquals(1, contactController.getAllContacts().size(), "All contacts should have one value");
    }

    @Test
    @DisplayName("Test contact added successfully")
    void testAddValidContact() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        // Action
        when(contactService.addContact(contact)).thenReturn(true);
        ResponseEntity<UUID> responseEntity = contactController.addContact(contact);

        // Test
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Contact should be added successfully");
    }

    @Test
    @DisplayName("Test no duplicate contacts")
    void testNoDuplicateContacts() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        // Action
        when(contactService.addContact(contact)).thenReturn(true, false);
        ResponseEntity<UUID> responseEntity = contactController.addContact(contact);
        ResponseEntity<UUID> responseEntity2 = contactController.addContact(contact);

        // Test
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Contact should be added successfully");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode(), "Duplicate contacts should not be added");
    }

    @Test
    @DisplayName("Test non existing contact not found")
    void getNotExistingContactByUUID_ShouldFail() {
        // Setup
        UUID uuid = UUID.randomUUID();

        // Action
        when(contactService.getContact(uuid)).thenThrow(new ContactNotFoundException("Contact not found"));
        ResponseEntity<Contact> responseEntity = contactController.getContact(uuid);

        // Test
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Contact should not be found by wrong id");
    }

    @Test
    @DisplayName("Test existing contact found by UUID")
    void getExistingContactByUUID_ShouldSucceed() {
        // Setup
        UUID uuid = UUID.randomUUID();
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345", "Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        // Action
        when(contactService.getContact(uuid)).thenReturn(contact);
        ResponseEntity<Contact> responseEntity = contactController.getContact(uuid);

        // Test
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Contact should be found by id");
    }

    @Test
    @DisplayName("Test existing contact found by name")
    void searchContact_ShouldSucceed() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        // Action
        when(contactService.searchContact(name)).thenReturn(contact);
        ResponseEntity<Contact> responseEntity = contactController.searchContact("John", "Doe");

        // Test
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Contact should be found");
    }

    @Test
    @DisplayName("Test valid contact deleted successfully")
    void deleteContact_ShouldSucceed() {
        // Setup
        UUID uuid = UUID.randomUUID();

        // Action
        ResponseEntity<Void> responseEntity = contactController.deleteContact(uuid);

        // Test
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Contact should be deleted successfully");
    }

    @Test
    @DisplayName("Test non existing contact not found")
    void deleteNotExistingContactByUUID_ShouldFail() {
        // Setup
        UUID uuid = UUID.randomUUID();

        // Action
        doThrow(new ContactNotFoundException("Contact not found")).when(contactService).deleteContact(uuid);
        ResponseEntity<Void> responseEntity = contactController.deleteContact(uuid);

        // Test
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Contact should not be found by wrong id");
    }
}