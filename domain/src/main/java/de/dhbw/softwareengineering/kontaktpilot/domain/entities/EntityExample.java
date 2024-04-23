package de.dhbw.softwareengineering.kontaktpilot.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test")
public class EntityExample {
    @Id
    private String id;
    @Column
    private String name;
   

    public EntityExample(String id, String name) {
    
        this.id = id;
        this.name = name;

    }

    protected EntityExample() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

   
}
