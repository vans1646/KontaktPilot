package de.dhbw.softwareengineering.kontaktpilot.plugins.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.softwareengineering.kontaktpilot.domain.entities.EntityExample;
@Repository
public interface springDataForBridge extends JpaRepository<EntityExample, String> {

}
