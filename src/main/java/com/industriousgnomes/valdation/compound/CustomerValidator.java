package com.industriousgnomes.valdation.compound;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CustomerValidator implements Validator {

	private final Validator addressValidator;
	
	/* Although we could autowire an AddressValidator into this validator,
	 * There would be no check to see if the validator actually supported
	 * the needed complex object.  Therefore, this should be the preferred
	 * process.
	 */
	public CustomerValidator(Validator addressValidator) {
		if (addressValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is " +
					"required and must not be null.");
		}
		if (!addressValidator.supports(Address.class)) {
			throw new IllegalArgumentException("The supplied [Validator] must " +
					"support the validation of [Address] instances.");
		}
		this.addressValidator = addressValidator;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surName", "field.required");

		Customer customer = (Customer)target;
		try {
			errors.pushNestedPath("address");
			ValidationUtils.invokeValidator(addressValidator, customer.getAddress(), errors);
		} finally {
			errors.popNestedPath();
		}
	}

}
