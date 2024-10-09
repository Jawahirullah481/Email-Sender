package com.jawa.emailsender.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jawa.emailsender.exception.NoSuchApplicantNameException;
import com.jawa.emailsender.model.Applicant;
import com.jawa.emailsender.repository.ApplicantRepository;

@Configuration
public class EmailSenderConfiguration implements WebMvcConfigurer {
	
	@Autowired
	private ApplicantRepository applicantRepository;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods("*")
				.allowedOriginPatterns("*")
				.allowedOrigins("http://localhost:3000");
			}
		};
	}
	
	@Bean
	public Applicant applicantInfo() {
		
		Optional<Applicant> applicantOptional = applicantRepository.findByName("Mohamed Rifayath");
		if(applicantOptional.isEmpty()) {
			throw new NoSuchApplicantNameException("Applicant name not found");
		}
		
		return applicantOptional.get();
		
	}
	
	
}
