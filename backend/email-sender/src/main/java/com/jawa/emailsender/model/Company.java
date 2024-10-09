package com.jawa.emailsender.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonFilter("CompanyFilter")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int companyId;
	private String companyName;
	private String mailId;
	private String location;
	private String place;
	private boolean isMailSent;
	private long phone;

	public Company() {
		isMailSent = false;
	}

	public Company(int companyId, String companyName, String mailId, String location, String place, long phone) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.mailId = mailId;
		this.location = location;
		this.place = place;
		this.isMailSent = false;
		this.phone = phone;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public boolean isMailSent() {
		return isMailSent;
	}

	public void setMailSent(boolean isMailSent) {
		this.isMailSent = isMailSent;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

}
