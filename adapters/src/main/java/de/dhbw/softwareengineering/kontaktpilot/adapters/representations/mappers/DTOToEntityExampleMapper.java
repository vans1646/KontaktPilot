package de.dhbw.softwareengineering.kontaktpilot.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.softwareengineering.kontaktpilot.adapters.representations.EntityExampleDTO;
import de.dhbw.softwareengineering.kontaktpilot.domain.entities.EntityExample;
//Beispiel macht keinen Sinn, da EntityExample und das DTO 1 zu 1 das gleiche sind.
// Würde benötigt werden bei komplexen Entitys die z.B. sich gegenseitig referenzieren.
// Solche Mapper lässt man im normal fall aus den entitys und dtos automatisch generieren, da wir aber bestimmte
// Zeilen anzahl schreiben müssen, macht es Sinn diese selbst zu schreiben :)

@Component
public class DTOToEntityExampleMapper implements Function<EntityExampleDTO, EntityExample> {

    @Autowired
    public DTOToEntityExampleMapper() {
    }

    @Override
    public EntityExample apply(EntityExampleDTO entityExampleDTO) {
    	
    	return new EntityExample(entityExampleDTO.getId(), entityExampleDTO.getName());
    }

    public EntityExample update(EntityExample oldExample, EntityExampleDTO newExample) {
        return new EntityExample(
        		oldExample.getId(),
                newExample.getName()
        );
    }

}
