package org.sample.spring.hello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PersonDaoJdbcImpl extends SimpleJdbcDaoSupport implements PersonDao {

	protected static final String FIND_PERSON_BY_CITY = "select id, name, surname, age from people where city = :city";
	protected static final String PERSON_INSERT = "insert into people( name, surname, age, city ) values (" +
			" :name, :surname, :age, :city );";
	protected static final String FIND_PERSON_BY_NAME_SURNAME = "select id, age, city from people where " +
			"name = :name and surname = :surname";
	protected static final String FIND_ALL_PEOPLE = "select id, name, surname, age from people";

	public List<Person> findByCity(final String city) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city", city);
		return getSimpleJdbcTemplate().query(
				FIND_PERSON_BY_CITY,
				new ParameterizedRowMapper<Person>(){
					public Person mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Person person = new Person();						
						person.setCity(city);
						
						int id = rs.getInt(1);
						if (!rs.wasNull()) {
							person.setId(id);	
						}
						
						person.setName(rs.getString(2));
						person.setSurname(rs.getString(3));
						
						int age = rs.getInt(4);
						if (!rs.wasNull())
							person.setAge(age);
						
						return person;
					}
				},
				params );	
	}

	public void insert(Person p) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("name", p.getName());
		args.put("surname", p.getSurname());
		args.put("age", p.getAge());
		args.put("city", p.getCity());
		getSimpleJdbcTemplate().update(PERSON_INSERT, args);
	}

	public Person findByNameAndSurname(final String name, final String surname) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("surname", surname);
		List<Person> people = getSimpleJdbcTemplate().query(
				FIND_PERSON_BY_NAME_SURNAME,
				new ParameterizedRowMapper<Person>(){
					public Person mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Person person = new Person();						
						person.setName(name);
						person.setSurname(surname);
						
						int id = rs.getInt(1);
						if (!rs.wasNull()) {
							person.setId(id);	
						}
						int age = rs.getInt(2);
						if (!rs.wasNull())
							person.setAge(age);
						person.setCity(rs.getString(3));					
						
						return person;
					}
				},
				params );
		
		return people.size() > 0 ? people.get(0) : null;
	}

	public List<Person> findAll() {
		List<Person> people = getSimpleJdbcTemplate().query( FIND_ALL_PEOPLE, null );		
		return people;
	}
	
}
