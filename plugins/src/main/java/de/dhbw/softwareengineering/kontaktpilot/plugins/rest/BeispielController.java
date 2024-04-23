package de.dhbw.softwareengineering.kontaktpilot.plugins.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeispielController {
	 @GetMapping("/test/{id}")
	    public String test(@PathVariable String id) {
	        return "test: " + id;
	    }
}
