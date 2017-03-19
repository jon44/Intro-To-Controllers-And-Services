package com.cooksys;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {
	
	@Mappings({
		@Mapping(source = "friendIds", target = "friends")
	})
	Person toPerson(PersonDto personDto);
	
	default List<Person> idToPerson(List<Long> friendIds) {
		List<Person> friends = new ArrayList<>();
		
		for(Long id : friendIds) {
			Person person = new Person();
			person.setID(id);
			friends.add(person);
		}
		
		return friends;
	}
	
	@Mappings({
		@Mapping(source = "friends", target = "friendIds")
	})
	PersonDto toPersonDto(Person person);
	
	default List<Long> personToId(List<Person> friends) {
		List<Long> friendIds = new ArrayList<>();
		for(Person person : friends) {
			friendIds.add(person.getID());
		}
		
		return friendIds;
	}
	
}
