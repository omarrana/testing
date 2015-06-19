package org.rdf2salesforce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

	private String accountId;
	private String department;
	private String description;
	private String name;
    private String firstName;
	private String lastName;
	private String mailingCity;
	private String mailingStreet;
	private String mailingState;
	private String mailingCountry;
	private String mailingPostalCode;
	private String mailingCountyCode;
	private String addressZip;
	private String phone;
	private String fax;
	private String mobilePhone;
	private String email;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMailingCity() {
		return mailingCity;
	}
	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}
	public String getMailingStreet() {
		return mailingStreet;
	}
	public void setMailingStreet(String mailingStreet) {
		this.mailingStreet = mailingStreet;
	}
	public String getMailingState() {
		return mailingState;
	}
	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}
	public String getMailingCountry() {
		return mailingCountry;
	}
	public void setMailingCountry(String mailingCountry) {
		this.mailingCountry = mailingCountry;
	}
	public String getMailingPostalCode() {
		return mailingPostalCode;
	}
	public void setMailingPostalCode(String mailingPostalCode) {
		this.mailingPostalCode = mailingPostalCode;
	}
	public String getMailingCountyCode() {
		return mailingCountyCode;
	}
	public void setMailingCountyCode(String mailingCountyCode) {
		this.mailingCountyCode = mailingCountyCode;
	}
	public String getAddressZip() {
		return addressZip;
	}
	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    


    
}
