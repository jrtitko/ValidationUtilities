package com.industriousgnomes.valdation.compound;

public class Customer {
	private String firstName;
	private String surName;
	private Address address;

	public Customer(String firstName, String surName, Address address) {
		this.firstName = firstName;
		this.surName = surName;
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
