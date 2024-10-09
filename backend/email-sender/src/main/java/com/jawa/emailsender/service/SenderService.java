package com.jawa.emailsender.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jawa.emailsender.exception.DuplicateCompanyMailIdException;
import com.jawa.emailsender.exception.InAppropriateTimeException;
import com.jawa.emailsender.model.Applicant;
import com.jawa.emailsender.model.Company;
import com.jawa.emailsender.repository.ApplicantRepository;
import com.jawa.emailsender.repository.CompanyRepository;

@Service
public class SenderService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private ApplicantRepository applicantRepository;
	@Autowired
	private MailService mailService;

	public List<Company> getAllCompanies() {

		return companyRepository.findAll();
	}

	public int addCompany(Company company) {
		
		String mailId = company.getMailId();
		Optional<Company> companyOptional = companyRepository.findByMailIdIgnoreCase(mailId);
		if(companyOptional.isPresent()) {
			throw new DuplicateCompanyMailIdException("Mail id already available for another company");
		}
		
		company = companyRepository.save(company);
		return company.getCompanyId();

	}

	public void editCompany(Company editedCompanyDetails) {
		
		int companyId = editedCompanyDetails.getCompanyId();
		String mailId = editedCompanyDetails.getMailId();
		Optional<Company> companyOptional = companyRepository.findByMailIdIgnoreCase(mailId);
		if(companyOptional.isPresent() && (companyOptional.get().getCompanyId() != companyId)) {
			throw new DuplicateCompanyMailIdException("Mail id already available for another company");
		}
		
		Company originalCompanyDetails = companyRepository.findById(companyId).get();
		originalCompanyDetails.setCompanyName(editedCompanyDetails.getCompanyName());
		originalCompanyDetails.setMailId(editedCompanyDetails.getMailId());
		originalCompanyDetails.setLocation(editedCompanyDetails.getLocation());
		originalCompanyDetails.setPlace(editedCompanyDetails.getPlace());
		originalCompanyDetails.setPhone(editedCompanyDetails.getPhone());
		companyRepository.save(originalCompanyDetails);
	}

	public void deleteCompany(int companyId) {
		companyRepository.deleteById(companyId);
	}

	/* --------Searching companies---------- */

	public List<Company> getCompanyByName(String companyName) {
		List<Company> companies = companyRepository.findByCompanyNameContainingIgnoreCase(companyName);
		return companies;
	}

	public List<Company> getCompanyByMailId(String mailId) {
		List<Company> companies = companyRepository.findByMailIdContainingIgnoreCase(mailId);
		return companies;
	}

	public List<Company> getCompanyByPlaceAndLocation(String place) {
		List<Company> companies = companyRepository.findByPlaceContainingIgnoreCaseOrLocationContainingIgnoreCase(place,
				place);
		return companies;
	}

	public List<Company> getMailSentCompanies() {
		return companyRepository.findByIsMailSentTrue();
	}

	public List<Company> getMailNotSentCompanies() {
		return companyRepository.findByIsMailSentFalse();
	}

	public void sendMailTo(List<Integer> companyIds) {

		LocalTime currentTime = LocalTime.now();
		int hour = currentTime.getHour();
		if (hour < 8 || hour > 18) {
			// You can mail only morning 8 o'clock to evening 6 o'clock.
			throw new InAppropriateTimeException("You can mail only from morning 8 am to evening 6pm");
		}

		companyIds.forEach(companyId -> {
			companyRepository.findById(companyId).ifPresent(company -> {
				if (!company.isMailSent()) {
					mailService.sendMail(company.getCompanyName(), company.getMailId());
					company.setMailSent(true);
					companyRepository.save(company);
				}
			});
		});

	}

	public void addUser(Applicant applicant) {
	
		applicantRepository.save(applicant);
		
	}

}
