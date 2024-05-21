package de.dhbw.softwareengineering.kontaktpilot.plugins.rest;

import de.dhbw.softwareengineering.kontaktpilot.application.services.ContactService;
import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.CategoryNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.exceptions.ContactNotFoundException;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Birthday;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactAddress;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactName;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/api/contacts")
@Tag(name = "Contacts", description = "Operations for managing contacts.")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String getDefault() {
        return "Hello World on Contacts!";
    }

    /*
        Endpoints /contact/*
        Interact with contacts
    */

    @GetMapping(path = "/contact/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts found"),
            @ApiResponse(responseCode = "404", description = "No contacts found")
    })
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping(path = "/contact/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact found"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Contact> getContact(@PathVariable UUID UUID) {
        try {
            return ResponseEntity.ok(contactService.getContact(UUID));
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/contact/search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact found"),
            @ApiResponse(responseCode = "404", description = "Could not find contact")
    })
    public ResponseEntity<Contact> searchContact(@RequestParam String firstName, @RequestParam String lastName) {
        Contact contact = contactService.searchContact(new ContactName(firstName, lastName));
        if (contact == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(contact);
        }
    }

    @PostMapping(path = "contact/add")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact added"),
            @ApiResponse(responseCode = "400", description = "Contact already exists")
    })
    public ResponseEntity<UUID> addContact(@RequestBody Contact contact) {
        if (contactService.addContact(contact)) {
            return ResponseEntity.ok(contact.getId());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/contact/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact deleted"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Void> deleteContact(@PathVariable UUID UUID) {
        try {
            contactService.deleteContact(UUID);
            return ResponseEntity.ok().build();
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/contact/update/name/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact name updated"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Contact> updateContactName(@PathVariable UUID UUID, @RequestBody ContactName name) {
        try {
            return ResponseEntity.ok(contactService.updateContactName(UUID, name));
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/contact/update/address/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact address updated"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Contact> updateContactAddress(@PathVariable UUID UUID, @RequestBody ContactAddress address) {
        try {
            return ResponseEntity.ok(contactService.updateContactAddress(UUID, address));
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/contact/update/email/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact email updated"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Contact> updateContactEmail(@PathVariable UUID UUID, @RequestParam String email) {
        try {
            return ResponseEntity.ok(contactService.updateContactEmail(UUID, email));
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/contact/update/phonenumber/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact phone number updated"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Contact> updateContactPhoneNumber(@PathVariable UUID UUID, @RequestParam String phoneNumber) {
        try {
            return ResponseEntity.ok(contactService.updateContactPhoneNumber(UUID, phoneNumber));
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/contact/update/category/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact category updated"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Contact> updateContactCategory(@PathVariable UUID UUID, @RequestParam Category category) {
        try {
            return ResponseEntity.ok(contactService.updateContactCategory(UUID, category));
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/contact/update/birthday/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact birthday updated"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Contact> updateContactBirthday(@PathVariable UUID UUID, @RequestParam Birthday birthday) {
        try {
            return ResponseEntity.ok(contactService.updateContactBirthday(UUID, birthday));
        } catch (ContactNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
        Endpoints /contact/category/*
        Interact with contacts by category
    */

    @GetMapping(path = "/category/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available categories"),
            @ApiResponse(responseCode = "404", description = "No categories found")
    })
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            return ResponseEntity.ok(contactService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/category/contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts found"),
            @ApiResponse(responseCode = "404", description = "No contacts found")
    })
    public ResponseEntity<List<Contact>> getContactsByCategory(@RequestParam String categoryName) {
        Category category = new Category(categoryName);
        try {
            return ResponseEntity.ok(contactService.getContactsByCategory(category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
