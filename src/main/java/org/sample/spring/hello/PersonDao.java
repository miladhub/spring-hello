package org.sample.spring.hello;

import java.util.List;

public interface PersonDao {
	public List<Person> findAll();
	
	public List<Person> findByCity(String city);

	public void insert(Person person);
	
	public Person findByNameAndSurname(String name, String surname);
}
