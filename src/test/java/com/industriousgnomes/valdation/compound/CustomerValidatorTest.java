package com.industriousgnomes.valdation.compound;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CustomerValidatorTest {

	CustomerValidator validator;
	Address address;
	
	@Before
	public void setUp() {
		validator = new CustomerValidator(new ValidAddessValidator());
		address = new Address();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidator_NullAddressValidator() {
		new CustomerValidator(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValidator_InvalidAddressValidator() {
		new CustomerValidator(new InvalidAddessValidator());
	}

	@Test
	public void testSupports() {
		assertTrue(validator.supports(Customer.class));
	}

	@Test
	public void testInvalidSupports() {
		assertFalse(validator.supports(String.class));
	}

	@Test
	public void testValidation_ValidCustomer() {
		Customer customer = new Customer("Joe", "Friday", address);
		Errors errors = new BeanPropertyBindingResult(customer, "customer");
		validator.validate(customer, errors);
		
		assertFalse(errors.hasErrors());
	}
	
	@Test
	public void testValidate_InvalidFirstName() {
		Customer customer = new Customer("", "Friday", address);
		Errors errors = new BeanPropertyBindingResult(customer, "customer");
		validator.validate(customer, errors);
		
		assertTrue(errors.hasErrors());
		assertEquals(1, errors.getErrorCount());
		validateFieldError(errors, "firstName", "field.required", "");
	}

	@Test
	public void testValidate_InvalidSurName() {
		Customer customer = new Customer("Joe", "", address);
		Errors errors = new BeanPropertyBindingResult(customer, "customer");
		validator.validate(customer, errors);
		
		assertTrue(errors.hasErrors());
		assertEquals(1, errors.getErrorCount());
		validateFieldError(errors, "surName", "field.required", "");
	}
	
	@Test
	public void testValidate_Address() {
		validator = new CustomerValidator(new ValidAddessValidatorInvalidAddress());

		Customer customer = new Customer("Joe", "Friday", address);
		Errors errors = new BeanPropertyBindingResult(customer, "customer");
		validator.validate(customer, errors);

		assertTrue(errors.hasErrors());
		assertEquals(1, errors.getErrorCount());
		validateFieldError(errors, "address.street", "field.required", null);		
	}

	private void validateFieldError(Errors errors, String field,
			String fieldMessage, String fieldValue) {
		assertTrue(errors.hasFieldErrors(field));
		assertEquals(1, errors.getFieldErrorCount(field));
		assertEquals(field, errors.getFieldError(field).getField());
		assertEquals(fieldMessage, errors.getFieldError(field).getCode());
		assertEquals(fieldValue, errors.getFieldError(field).getRejectedValue());
		assertFalse(errors.getFieldError(field).isBindingFailure());
	}

	// ======================================================
	
	private class ValidAddessValidator implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			return true;
		}

		@Override
		public void validate(Object target, Errors errors) {
			// TODO Auto-generated method stub
		}		
	}

	private class InvalidAddessValidator implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			return false;
		}

		@Override
		public void validate(Object target, Errors errors) {
			// TODO Auto-generated method stub
		}		
	}

	private class ValidAddessValidatorInvalidAddress implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			return true;
		}

		@Override
		public void validate(Object target, Errors errors) {
			ValidationUtils.rejectIfEmpty(errors, "street", "field.required");
		}		
	}
}
