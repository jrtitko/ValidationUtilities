package com.industriousgnomes.valdation.annotation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersonFormTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPersonForm() {
		//new PersonForm("Super Fantastic Fun Guy", -1);
		PersonForm p = new PersonForm();
		p.age = -1;
	}

	
}
