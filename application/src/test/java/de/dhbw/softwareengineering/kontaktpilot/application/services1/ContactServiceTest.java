package de.dhbw.softwareengineering.kontaktpilot.application.services1;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.ContactNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.repositories.ContactBridgeRepository;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactBridgeRepository contactBridgeRepository;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactService(contactBridgeRepository);
    }

    @Test
    @DisplayName("Test get all contacts is empty")
    void getAllContacts() {
        // Action
        when(contactBridgeRepository.getAllContacts()).thenReturn(Collections.emptyList());

        // Test
        assertEquals(0, contactService.getAllContacts().size(), "All contacts should be empty");
    }

    @Test
    @DisplayName("Test get contact by UUID")
    void getContact_ShouldSucceed() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        // Action
        when(contactBridgeRepository.getContact(contact.getId())).thenReturn(contact);

        // Test
        assertEquals(contact, contactService.getContact(contact.getId()), "Contact should be found");
    }

    @Test
    @DisplayName("Test delete contact by UUID")
    void deleteContact_ShouldSucceed() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        //Action
        doNothing().when(contactBridgeRepository).deleteContact(contact.getId());
        when(contactService.getContact(contact.getId())).thenThrow(new ContactNotFoundException("Contact not found"));
        contactService.deleteContact(contact.getId());

        // Test
        assertThrows(ContactNotFoundException.class, () -> contactService.getContact(contact.getId()), "Contact should not be found");
    }

    @Test
    @DisplayName("Test get contacts by category")
    void getContactsByCategory_ShouldOnlyContainRightContacts() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        ContactName name2 = new ContactName("Max", "Mustermann");
        ContactAddress address2 = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category2 = new Category("Friends");
        Birthday birthday2 = new Birthday(24, 12, 2002);
        Contact contact2 = new Contact(name2, address2, "ben.doever@mail.com", "1234567890", category2, birthday2);

        // Action
        when(contactBridgeRepository.getContactsByCategory(category)).thenReturn(Collections.singletonList(contact));
        when(contactBridgeRepository.getContactsByCategory(category2)).thenReturn(Collections.singletonList(contact2));

        // Test
        assertEquals(Collections.singletonList(contact), contactService.getContactsByCategory(category), "Contacts should be found");
        assertEquals(Collections.singletonList(contact2), contactService.getContactsByCategory(category2), "Contacts should be found");
    }

    @Test
    @DisplayName("Test get all categories")
    void getAllCategories_ShouldContainAll() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        ContactName name2 = new ContactName("Ben", "Doever");
        ContactAddress address2 = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category2 = new Category("Friends");
        Birthday birthday2 = new Birthday(24, 12, 2002);
        Contact contact2 = new Contact(name2, address2, "ben.doever@mail.com", "1234567890", category2, birthday2);

        List<Category> allCategories = Arrays.asList(category, category2);

        // Action
        when(contactBridgeRepository.getAllCategories()).thenReturn(allCategories);

        // Test
        assertEquals(allCategories.contains(category), contactService.getAllCategories().contains(category), "All categories should be found");
        assertEquals(allCategories.contains(category2), contactService.getAllCategories().contains(category2), "All categories should be found");
    }

    @Test
    @DisplayName("Test search contact by name")
    void searchContact_ShouldSucceed() {
        // Setup
        ContactName name = new ContactName("John", "Doe");
        ContactAddress address = new ContactAddress("Main Street", "1234", "12345","Springfield");
        Category category = new Category("Family");
        Birthday birthday = new Birthday(24, 7, 2002);
        Contact contact = new Contact(name, address, "john.doe@mail.com", "1234567890", category, birthday);

        // Action
        when(contactBridgeRepository.getAllContacts()).thenReturn(Collections.singletonList(contact));

        // Test
        assertEquals(contact, contactService.searchContact(name), "Contact should be found");
    }
}