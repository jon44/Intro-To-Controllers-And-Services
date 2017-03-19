package com.cooksys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service 
public class PersonService {
	
	private AtomicInteger idGenerator = new AtomicInteger(0);
	private HashMap<Long, Person> personMap = new HashMap<>();
	private final PersonMapper personMapper;
	
	public PersonService(PersonMapper personMapper) {
		super();
		this.personMapper = personMapper;
	}
	
	public HashMap<Long, Person> getPersonMap() {
		return this.personMap;
	}
	
	public Collection<PersonDto> getAll() {
		Collection<PersonDto> personDtos = new ArrayList<>();
		PersonDto personDto;
		
		for(Person p : personMap.values()) {
			personDto = personMapper.toPersonDto(p);
			personDtos.add(personDto);
		}
			
		return personDtos;
	}
	
	public PersonDto get(long id) {
		if(personMap.containsKey(id)) {
			PersonDto personDto = personMapper.toPersonDto(personMap.get(id));
			return personDto;
		}
		return null;
	}
	
	public long add(PersonDto personDto) {
		Person person = personMapper.toPerson(personDto);		
		if(!buildFriends(person))
			return -1;
		
		person.setID(idGenerator.incrementAndGet());
		personMap.put(person.getID(), person);
		return person.getID();
	}
	
	public boolean put(long id, PersonDto personDto) {
		Person person = personMapper.toPerson(personDto);
		
		if(!personMap.containsKey(id))
			return false;
		
		personMap.put(id, person);
		return true;
	}
	
	public boolean delete(long id) {
		if(!personMap.containsKey(id))
			return false;
		
		//This doesn't do what was intended
		for(Person person : personMap.values()) {
			for(Person friend : person.getFriends()) {
				if(friend.getID() == id) {
					List<Person> personsFriends = person.getFriends();
					personsFriends.remove(id);
					person.setFriends(personsFriends);
				}
			}
		}
		
		personMap.remove(id);
		return true;
	}
	
	public boolean buildFriends(Person person) {
		
		for(Person p : person.getFriends()) {
			
			if(!personMap.containsKey(p.getID()))
				return false;
			
			Person tempPerson = personMap.get(p.getID());
			p.setFirstName(tempPerson.getFirstName());
			p.setLastName(tempPerson.getLastName());
			p.setFriends(tempPerson.getFriends());
		}
		
		return true;
	}
}
