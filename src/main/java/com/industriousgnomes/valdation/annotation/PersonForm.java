package com.industriousgnomes.valdation.annotation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonForm {

	@NotNull
	@Size(min=2, max=30)
	String name;

	@NotNull
	@Min(18)
	int age;

	public PersonForm() {}
	
	public PersonForm(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String toString() {
        return "Person(Name: " + name + ", Age: " + age + ")";
    }
}
