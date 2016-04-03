package com.industriousgnomes.valdation.simple;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;

import com.industriousgnomes.valdation.simple.Person;
import com.industriousgnomes.valdation.simple.PersonValidator;

public class PersonValidatorTest {

	PersonValidator validator;
	
	@Before
	public void setUp() {
		validator = new PersonValidator();
	}
	
	@Test
	public void testSupports() {
		assertTrue(validator.supports(Person.class));
	}

	@Test
	public void testInvalidSupports() {
		assertFalse(validator.supports(String.class));
	}
	
	@Test
	public void testValidation_ValidPerson() {
		Person person = new Person("Joe", 35);
		Errors errors = new BeanPropertyBindingResult(person, "person");
		validator.validate(person, errors);
		
		assertFalse(errors.hasErrors());
	}

	@Test
	public void testValidation_NameEmpty() {
		Person person = new Person(null, 35);
		Errors errors = new BeanPropertyBindingResult(person, "person");
		validator.validate(person, errors);
		
		assertTrue(errors.hasErrors());
		assertEquals(1, errors.getErrorCount());
		assertTrue(errors.hasFieldErrors("name"));
		assertEquals(1, errors.getFieldErrorCount("name"));
		assertEquals("name", errors.getFieldError("name").getField());
		assertEquals("name.empty", errors.getFieldError("name").getCode());
		assertEquals(null, errors.getFieldError("name").getRejectedValue());
		assertFalse(errors.getFieldError("name").isBindingFailure());
	}

	@Test
	public void testValidation_AgeTooLow() {
		Person person = new Person("Joe", -1);
		Errors errors = new BeanPropertyBindingResult(person, "person");
		validator.validate(person, errors);
		
		assertTrue(errors.hasErrors());
		assertEquals(1, errors.getErrorCount());
		assertTrue(errors.hasFieldErrors("age"));
		assertEquals(1, errors.getFieldErrorCount("age"));
		assertEquals("age", errors.getFieldError("age").getField());
		assertEquals("negative.value", errors.getFieldError("age").getCode());
		assertEquals(-1, errors.getFieldError("age").getRejectedValue());
		assertFalse(errors.getFieldError("age").isBindingFailure());
	}

	@Test
	public void testValidation_AgeTooHigh() {
		Person person = new Person("Joe", 200);
		Errors errors = new BeanPropertyBindingResult(person, "person");
		validator.validate(person, errors);
		
		assertTrue(errors.hasErrors());
		assertEquals(1, errors.getErrorCount());
		assertTrue(errors.hasFieldErrors("age"));
		assertEquals(1, errors.getFieldErrorCount("age"));
		assertEquals("age", errors.getFieldError("age").getField());
		assertEquals("too.darn.old", errors.getFieldError("age").getCode());
		assertEquals(200, errors.getFieldError("age").getRejectedValue());
		assertFalse(errors.getFieldError("age").isBindingFailure());
	}
}
