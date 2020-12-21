package com.mahanandig.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahanandig.sample.dto.Person;
import com.mahanandig.sample.exception.ResourceNotFoundException;
import com.mahanandig.sample.repository.PersonRepository;

@RestController
@RequestMapping("/endpoint")
public class SampleController {
	
	
	 @Autowired
	 private PersonRepository personRepository;
	 
	 @GetMapping("/persons")
	    public List < Person > getAllPersons() {
	        return personRepository.findAll();
	    }
	
	 @GetMapping("person/{id}")
	 public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personId ) throws ResourceNotFoundException {
		 Person person = personRepository.findById(personId)
				 .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));
		return ResponseEntity.ok().body(person);
		 
	 }
	@PostMapping("/person")
	public  Person createPerson(@RequestBody Person person) {
		
		return  personRepository.save(person);
		
	}
	
	@DeleteMapping("/person/{id}")
    public Map < String, Boolean > deleteEmployee(@PathVariable(value = "id") Long personId)
    throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));

        personRepository.delete(person);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
