package de.dhbw.softwareengineering.kontaktpilot.plugins.rest;

import de.dhbw.softwareengineering.kontaktpilot.application.services1.ContactService;
import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
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
        return "Hello World";
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
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "contact/add")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact added"),
            @ApiResponse(responseCode = "409", description = "Contact already exists")
    })
    public ResponseEntity<ApiResponse> addContact(@RequestBody Contact contact) {
        if (contactService.addContact(contact)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(409).build();
        }
    }

    @DeleteMapping(path = "/contact/{UUID}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact deleted"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<ApiResponse> deleteContact(@PathVariable UUID UUID) {
        try {
            contactService.deleteContact(UUID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
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


    @GetMapping(path = "/category/{category}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts found"),
            @ApiResponse(responseCode = "404", description = "No contacts found")
    })
    public ResponseEntity<List<Contact>> getContactsByCategory(Category category) {
        try {
            return ResponseEntity.ok(contactService.getContactsByCategory(category));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}