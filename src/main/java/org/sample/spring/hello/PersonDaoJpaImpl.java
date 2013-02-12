package org.sample.spring.hello;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.jpa.support.JpaDaoSupport;

public class PersonDaoJpaImpl extends JpaDaoSupport implements PersonDao {

	@SuppressWarnings("unchecked")
	public List<Person> findByCity(String city) {
		Map<String, Object> pars = new HashMap<String, Object>();
		pars.put("city", city);
		return (List<Person>) getJpaTemplate().findByNamedQueryAndNamedParams("people-findByCity", pars);
	}

	@SuppressWarnings("unchecked")
	public Person findByNameAndSurname(String name, String surname) {
		Map<String, Object> pars = new HashMap<String, Object>();
		pars.put("name", name);
		pars.put("surname", surname);
		List<Person> matches = 
			(List<Person>) getJpaTemplate()
			.findByNamedQueryAndNamedParams("people-findByNameSurname", pars);
		return matches.size() > 0 ? matches.get(0) : null;
	}

	public void insert(Person person) {
		getJpaTemplate().persist(person);
	}

	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		return (List<Person>) getJpaTemplate().findByNamedQuery("people-findAll");
	}

}
