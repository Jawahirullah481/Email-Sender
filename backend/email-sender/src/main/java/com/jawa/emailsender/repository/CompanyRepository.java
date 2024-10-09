package com.jawa.emailsender.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jawa.emailsender.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	List<Company> findByCompanyNameContainingIgnoreCase(String companyName);
	
	Optional<Company> findByMailIdIgnoreCase(String mailId);

	List<Company> findByMailIdContainingIgnoreCase(String mailId);
	
    List<Company> findByPlaceContainingIgnoreCaseOrLocationContainingIgnoreCase(String place, String location);
    
    List<Company> findByIsMailSentFalse();
    
    List<Company> findByIsMailSentTrue();


}
