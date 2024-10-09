package com.jawa.emailsender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jawa.emailsender.exception.MailNotSentException;
import com.jawa.emailsender.helper.RefreshableInputStreamResource;
import com.jawa.emailsender.model.Applicant;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Applicant applicant;


	public void sendMail(String companyName, String companyMail) {
		
		
		System.out.println("Email Sender Log : " + "Company Name is : " + companyName + " Company Mail is : " + companyMail);
		
		// --------- Mail Templates ----------
		
		String mailSubject = "Application for " + applicant.getRole() + " Role at " + companyName;
		String mailContent = "Hi sir,\n\n" + "I hope this email finds you well. My name is " + applicant.getName()
				+ ", and I am a recent " + applicant.getDegree()
				+ " graduate with a strong background in developing web applications using Spring Boot and React, and also have knowledge in MySql. I am eager to contribute my skills and passion to a dynamic and innovative team.\n\n"
				+ "With this email, I have provided a link to my personal website, providing you with a detailed overview of my projects and skills.\n\n"
				+ "Portfolio website : " + applicant.getPortfolioWebsiteLink() + "\n\n"
				+ "I have attached my resume to provide an overview of my educational background, technical skills, and professional experiences. I am excited about the opportunity to contribute to the success of " + companyName + " as a " + applicant.getRole() +".\n\n"
				+ "Thank you for considering my application. I am eager to discuss how my skills align with the needs of " + companyName + ".\n\n"
				+ "I look forward to the opportunity for an interview to further discuss how I can contribute to the success of your team.\n\n"
				+ "Best Regards,\n\n" + applicant.getName() + ",\n" + applicant.getPlace() + ", " + applicant.getState() + ", " + applicant.getCountry() + ",\n" + "Mail: " + applicant.getEmail() + ",\n" + "Phone: " + applicant.getPhone();
		
		// For Rifayath
		mailContent = "Dear Recruiter,\n\n"
				+ "I hope this email finds you well. My name is Rifayath, and I am writing to express my interest in potential opportunities as a web developer within your esteemed organization.\n\n"
				+ "With a strong background in web development and a passion for creating engaging and user-friendly websites, I am excited about the possibility of contributing to your team. I have 6 months of experience working with HTML, CSS, JavaScript, and I am proficient in Angular.\n\n"
				+ "Throughout my career, I have successfully completed numerous projects, ranging from e-commerce platforms to content management systems, demonstrating my ability to deliver high-quality work within deadlines. I am adept at collaborating with cross-functional teams and thrive in dynamic environments where creativity and innovation are valued.\n\n"
				+ "I am particularly drawn to  the company's commitment to innovation, its focus on user experience and I am eager to contribute my skills and expertise to help achieve its goals.\n\n"
				+ "Thank you for considering my application. I look forward to the possibility of discussing this exciting opportunity with you further.\n\n"
				+ "Best Regards,\n\n" + applicant.getName() + ",\n" + applicant.getPlace() + ", " + applicant.getState() + ", " + applicant.getCountry() + ",\n" + "Mail: " + applicant.getEmail() + ",\n" + "Phone: " + applicant.getPhone();
		
		// Rifayath 2
		mailContent = "Dear Recruiter,\n\n"
				+ "I hope this email finds you well. My name is Rifayath, and I am writing to express my interest in potential opportunities as a web developer within your esteemed organization.\n\n"
				+ "With a strong background in web development and a passion for creating engaging and user-friendly websites, I am excited about the possibility of contributing to your team. I have 6 months of experience working with HTML, CSS, JavaScript, and I am proficient in Angular.\n\n"
				+ "I am particularly drawn to  the company's commitment to innovation, its focus on user experience and I am eager to contribute my skills and expertise to help achieve its goals.\n\n"
				+ "I have attached my resume for your review, which provides additional details about my professional background and accomplishments. I would welcome the opportunity to discuss how my experience aligns with your needs and how I can contribute to the success of your team.\n\n"
				+ "Thank you for considering my application. I look forward to the possibility of discussing this exciting opportunity with you further.\n\n"
				+ "Warm Regards,\n" + applicant.getName() + ",\n" + "Mail: " + applicant.getEmail() + ",\n" + "Phone: " + applicant.getPhone();

		System.out.println(mailSubject);
		System.out.println(mailContent);

		// -------------- Sending Mail ------------------
		sendMail(companyMail, mailSubject, mailContent);
		
	}
	

	private void sendMail(String to, String subject, String body) {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, false);
			
			// ---------Attaching resume----------
			
			// Attach the PDF file from the classpath
	        ClassPathResource resumeResource = new ClassPathResource("Resume.pdf");
	        InputStreamSource inputStreamSource = new RefreshableInputStreamResource(resumeResource);
	        helper.addAttachment("Resume.pdf", inputStreamSource);
			
			// ---------Resume attached------------
			
			javaMailSender.send(mimeMessage);
		} catch (MessagingException exception) {
			throw new MailNotSentException("Mail cannot sent to " + to);
		}
		

	}

}
