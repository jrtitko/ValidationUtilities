package com.industriousgnomes.valdation.simple;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PersonValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		Person person = (Person) target;
		if (person.getAge() < 0) {
			errors.rejectValue("age", "negative.value");
		} else if (person.getAge() > 100) {
			errors.rejectValue("age", "too.darn.old");
		}
	}

}
