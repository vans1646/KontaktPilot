package de.dhbw.softwareengineering.kontaktpilot.domain.services;

import com.opencsv.CSVReader;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Birthday;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactAddress;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class ContactImportExportServiceTest {

    private ContactImportExportService service = new ContactImportExportService();

    @Test
    @DisplayName("Test import contacts with valid data")
    public void testImportContacts_ShouldSucceed() throws Exception {
        // Setup
        URL resourceUrl = getClass().getClassLoader().getResource("valid_test_data.csv");
        String path = resourceUrl.getPath();

        // Action
        List<Contact> contacts = service.importContacts(path);

        // Test
        assertEquals(1, contacts.size());
        Contact contact = contacts.get(0);
        assertEquals(new ContactName("John", "Doe"), contact.getName());
        assertEquals("1234567890", contact.getPhoneNumber());
        assertEquals("john.doe@example.com", contact.getEmail());
        assertEquals(new ContactAddress("Main Street", "1234", "12345", "Springfield"), contact.getAddress());
        assertEquals(new Category("Family"), contact.getCategory());
        assertEquals(new Birthday(24, 7, 2002), contact.getBirthday());
    }

    @Test
    @DisplayName("Test import contacts with invalid data")
    public void testImportContacts_ShouldFail() throws IOException {
        // Setup
        URL resourceUrl = getClass().getClassLoader().getResource("invalid_test_data.csv");
        String path = resourceUrl.getPath();

        // Test
        assertThrows(RuntimeException.class, () -> service.importContacts(path));
    }

    @Test
    @DisplayName("Test export contacts")
    public void testExportContacts_ShouldSucceed() throws Exception {
        // Setup
        String path = "src/test/resources/export_test_data.csv";
        List<Contact> contacts = new ArrayList<>();
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);
        contacts.add(contact);

        // Action
        service.exportContacts(path, contacts);

        // Test
        CSVReader reader = new CSVReader(new FileReader(path));
        List<String[]> csvContacts = reader.readAll();
        assertEquals(1, csvContacts.size());
        String[] csvContact = csvContacts.get(0);
        assertEquals("John", csvContact[0]);
        assertEquals("Doe", csvContact[1]);
        assertEquals("1234567890", csvContact[2]);
        assertEquals("john.doe@mail.com", csvContact[3]);
        assertEquals("Main Street", csvContact[4]);
        assertEquals("1234", csvContact[5]);
        assertEquals("12345", csvContact[6]);
        assertEquals("Springfield", csvContact[7]);
        assertEquals("Family", csvContact[8]);
        assertEquals("24-7-2002", csvContact[9]);
    }

    @Test
    @DisplayName("Test export contacts with invalid path")
    public void testExportContacts_ShouldFail() {
        // Setup
        String path = null;
        List<Contact> contacts = new ArrayList<>();
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);
        contacts.add(contact);

        // Test
        assertThrows(RuntimeException.class, () -> service.exportContacts(null, contacts));
    }
}