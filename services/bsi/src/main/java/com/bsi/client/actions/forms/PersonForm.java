package com.bsi.client.actions.forms;


public class PersonForm extends LocationForm {
	private Long id;
	private String name;
	private String website;
	private String description;
	
	private String email;
	private String address;
	private String countryCode;
	private String oacNumber;
	private String isOACAuthenticated="N";
	private String phoneNumber;
	private String personType;
	private String password;
	private String[] serviceId;
	private String serviceIdName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getPersonType() {
		return personType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setServiceId(String[] serviceId) {
		this.serviceId = serviceId;
	}

	public String[] getServiceId() {
		return serviceId;
	}

	public void setServiceIdName(String serviceIdName) {
		this.serviceIdName = serviceIdName;
	}

	public String getServiceIdName() {
		return serviceIdName;
	}

	public String getOacNumber() {
		return oacNumber;
	}

	public void setOacNumber(String oacNumber) {
		this.oacNumber = oacNumber;
	}

	public String getIsOACAuthenticated() {
		return isOACAuthenticated;
	}

	public void setIsOACAuthenticated(String isOACAuthenticated) {
		this.isOACAuthenticated = isOACAuthenticated;
	}
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
