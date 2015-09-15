package com.bsi.common.beans;

import java.util.HashSet;
import java.util.Set;

public class Person {

	public Long id;
	public String name;
	public String address;

	public String website;
	public String description;
	public String oacNumber;
	public String isOACAuthenticated;
	
	private String countryCode;
	private String phoneNumber;
	private String email;
	private String password;
	private String personType;
	private String isActive;
	private Set services = new HashSet();
	private Set requestedSevices = new HashSet();
	private Set offeredServices = new HashSet();
	private Set administeredServices = new HashSet();

	private Set groups = new HashSet();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setIsOACAuthenticated(String isOACAuthenicated) {
		this.isOACAuthenticated = isOACAuthenicated;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set getServices() {
		return services;
	}

	public void setServices(Set services) {
		this.services = services;
	}

	public Set getRequestedSevices() {
		return requestedSevices;
	}

	public void setRequestedSevices(Set requestedSevices) {
		this.requestedSevices = requestedSevices;
	}

	public Set getOfferedServices() {
		return offeredServices;
	}

	public void setOfferedServices(Set offeredServices) {
		this.offeredServices = offeredServices;
	}

	public Set getAdministeredServices() {
		return administeredServices;
	}

	public void setAdministeredServices(Set administeredServices) {
		this.administeredServices = administeredServices;
	}

	public Set getGroups() {
		return groups;
	}

	public void setGroups(Set groups) {
		this.groups = groups;
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
