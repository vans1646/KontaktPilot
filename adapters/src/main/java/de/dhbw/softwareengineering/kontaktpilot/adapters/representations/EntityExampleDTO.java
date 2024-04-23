package de.dhbw.softwareengineering.kontaktpilot.adapters.representations;

//DTO = Data Transfer Object, Quasi um komplexe objekte anzeigen zu können, bzw nur relevante Informationen
// zu verarbeiten
public class EntityExampleDTO {
    private String id;
    private String name;

    public EntityExampleDTO(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setId(String id) {
        this.id = id;
    }
}

