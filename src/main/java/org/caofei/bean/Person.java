package org.caofei.bean;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.functors.InstantiateFactory;
import org.apache.commons.collections.list.LazyList;

public class Person {
	private String id;
	private String name;
	private String age;
	
	private List<PersonDetail> personList = LazyList.decorate(new LinkedList<PersonDetail>() ,new InstantiateFactory(PersonDetail.class));
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public List<PersonDetail> getPersonList() {
		return personList;
	}
	public void setPersonList(List<PersonDetail> personList) {
		this.personList = personList;
	}
}
