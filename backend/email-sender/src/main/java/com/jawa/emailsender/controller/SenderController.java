package com.jawa.emailsender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jawa.emailsender.model.Applicant;
import com.jawa.emailsender.model.Company;
import com.jawa.emailsender.service.SenderService;

@RestController
public class SenderController {

	@Autowired
	private SenderService senderService;

	@GetMapping("/companies")
	public MappingJacksonValue getAllCompanies() {
		List<Company> companies = senderService.getAllCompanies();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(companies);
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("CompanyFilter",
				SimpleBeanPropertyFilter.serializeAll());
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

	@GetMapping("/companies/filter")
	public MappingJacksonValue filterCompanies(@RequestParam("q") String filterQuery) {
		List<Company> companies = null;
		if(filterQuery.equalsIgnoreCase("SENT")) {
			companies = senderService.getMailSentCompanies();
		} else if(filterQuery.equalsIgnoreCase("ALL")) {
			companies = senderService.getAllCompanies();
		} else {
			companies = senderService.getMailNotSentCompanies();
		}
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(companies);
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("CompanyFilter",
				SimpleBeanPropertyFilter.serializeAll());
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

	@PostMapping("/companies")
	public int addCompany(@RequestBody Company company) {
		return senderService.addCompany(company);
	}

	@PutMapping("/companies")
	public ResponseEntity<Object> editCompany(@RequestBody Company company) {
		senderService.editCompany(company);
		return successResponseEntity();
	}

	@DeleteMapping("/companies/{companyId}")
	public ResponseEntity<Object> deleteCompany(@PathVariable int companyId) {
		senderService.deleteCompany(companyId);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	// ---------- Searching companies

	@GetMapping("/companies/search-by-name/{companyName}")
	public MappingJacksonValue getBookByName(@PathVariable String companyName) {
		List<Company> companies = senderService.getCompanyByName(companyName);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(companies);
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("CompanyFilter",
				SimpleBeanPropertyFilter.serializeAll());
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

	@GetMapping("/companies/search-by-mail/{mailId}")
	public MappingJacksonValue getBookByMailId(@PathVariable String mailId) {
		List<Company> companies = senderService.getCompanyByMailId(mailId);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(companies);
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("CompanyFilter",
				SimpleBeanPropertyFilter.serializeAll());
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

	@GetMapping("/companies/search-by-place/{place}")
	public MappingJacksonValue getBookByPlaceAndLocation(@PathVariable String place) {
		List<Company> companies = senderService.getCompanyByPlaceAndLocation(place);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(companies);
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("CompanyFilter",
				SimpleBeanPropertyFilter.serializeAll());
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

	@PostMapping("/companies/send-mail")
	public ResponseEntity<Object> sendMail(@RequestBody List<Integer> companyIds) {
		senderService.sendMailTo(companyIds);
		return successResponseEntity();
	}
	
	// ------------ Add User -------------------
	@PostMapping("/applicant/add-user")
	public ResponseEntity<Object> addUser(@RequestBody Applicant applicant) {
		senderService.addUser(applicant);
		return successResponseEntity();
	}
	

	public ResponseEntity<Object> successResponseEntity() {
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}

/*
 * 
 * 1. show all companies - GET * 2. add company - POST * 3. edit company - PUT *
 * 4. delete company - DELETE * 5. send mail - POST 6. get company by name,
 * mailid, place * 7. filter only sent companies 8. filter only not sent
 * companies 9. filter all companies
 * 
 * 
 * 
 * 
 */
