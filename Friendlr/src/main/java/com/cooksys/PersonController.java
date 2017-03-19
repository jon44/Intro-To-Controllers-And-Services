package com.cooksys;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("person")
@RestController
public class PersonController {

	private final PersonService personService;
	
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping
	public Collection<PersonDto> getAllPersons(HttpServletResponse response) {
		return personService.getAll();
	}
	
	@GetMapping("{id}")
	public PersonDto getPerson(@PathVariable long id, HttpServletResponse response) {
		PersonDto personDto = personService.get(id);
		if(personDto == null)
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return personDto;
	}
	
	@PostMapping
	public long add(@RequestBody PersonDto personDto, HttpServletResponse response) {
		long result = personService.add(personDto);
		if(result > 0)
			response.setStatus(HttpServletResponse.SC_CREATED);
		if(result == -1)
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return result;

	}
	
	@PutMapping("{id}")
	public void put(@PathVariable long id, @RequestBody PersonDto personDto, HttpServletResponse response) {
		personDto.setID(id);
		if(!personService.put(id, personDto))
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable long id, HttpServletResponse response) {
		if(!personService.delete(id))
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
}
