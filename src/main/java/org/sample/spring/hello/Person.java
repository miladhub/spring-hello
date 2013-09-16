package org.sample.spring.hello;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="people")
@NamedQueries({
    @NamedQuery(
            name="people-findByCity",
            query="select p from Person p where p.city = :city"        
    ),
    @NamedQuery(
            name="people-findByNameSurname",
            query="select p from Person p where p.name = :name and p.surname = :surname"        
    ),
    @NamedQuery(
            name="people-findAll",
            query="select p from Person p"        
    )
})
public class Person implements Serializable {

	private static final long serialVersionUID = 548155036033355983L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String surname;
	private String city;
	
	@Type(type = "com.qrmedia.commons.persistence.hibernate.usertype.XStreamableUserType")
	@Column(name = "attributesAsMap")
	private Map<String, Object> attributesAsMap = new HashMap<String, Object>();
	
	private int age;
	
	public Person() {}
	
	public Person(String name, String surname, String city, int age, Map<String, Object> attributesAsMap) {
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.age = age;
		this.attributesAsMap = attributesAsMap;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", city=" + city + ", map=" + attributesAsMap + ", age=" + age + "]";
	}
}
