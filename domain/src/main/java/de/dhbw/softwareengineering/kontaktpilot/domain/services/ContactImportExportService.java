package de.dhbw.softwareengineering.kontaktpilot.domain.services;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import de.dhbw.softwareengineering.kontaktpilot.domain.entities.Contact;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Birthday;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.Category;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactAddress;
import de.dhbw.softwareengineering.kontaktpilot.domain.values.ContactName;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactImportExportService {
    public List<Contact> importContacts(String path) {
        List<Contact> contacts = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            List<String[]> csvContacts = reader.readAll();
            for (String[] csvContact : csvContacts) {
                Contact newContact = new Contact();
                newContact.setName(new ContactName(csvContact[0], csvContact[1]));
                newContact.setPhoneNumber(csvContact[2]);
                newContact.setEmail(csvContact[3]);
                newContact.setAddress(new ContactAddress(csvContact[4], csvContact[5], csvContact[6], csvContact[7]));
                newContact.setCategory(new Category(csvContact[8]));
                String[] birthdayParts = csvContact[9].split("-");
                int day = Integer.parseInt(birthdayParts[0]);
                int month = Integer.parseInt(birthdayParts[1]);
                int year = Integer.parseInt(birthdayParts[2]);
                newContact.setBirthday(new Birthday(day, month, year));
                contacts.add(newContact);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }

    public void exportContacts(String path, List<Contact> contacts) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(path));
            for (Contact contact : contacts) {
                String[] csvContact = new String[10];
                csvContact[0] = contact.getName().getFirstName();
                csvContact[1] = contact.getName().getLastName();
                csvContact[2] = contact.getPhoneNumber();
                csvContact[3] = contact.getEmail();
                csvContact[4] = contact.getAddress().getStreet();
                csvContact[5] = contact.getAddress().getHouseNumber();
                csvContact[6] = contact.getAddress().getZipCode();
                csvContact[7] = contact.getAddress().getCity();
                csvContact[8] = contact.getCategory().getName();
                Birthday birthday = contact.getBirthday();
                csvContact[9] = String.format("%d-%d-%d", birthday.getBirthday(), birthday.getBirthmonth(), birthday.getBirthyear());
                writer.writeNext(csvContact);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}