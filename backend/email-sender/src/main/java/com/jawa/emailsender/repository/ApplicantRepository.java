package com.jawa.emailsender.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jawa.emailsender.model.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {

	Optional<Applicant> findByName(String applicantName);
	
}
