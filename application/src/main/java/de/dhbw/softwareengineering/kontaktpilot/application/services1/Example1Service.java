package de.dhbw.softwareengineering.kontaktpilot.application.services1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.softwareengineering.kontaktpilot.domain.repositories.BridgeRepository;

@Service
public class Example1Service {
	@Autowired
    private BridgeRepository repository;
    
    public void deleteById(Long id) {
        repository.deleteById(id);
       
    }
}
