package com.jawa.emailsender.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jawa.emailsender.exception.DuplicateCompanyMailIdException;
import com.jawa.emailsender.exception.InAppropriateTimeException;
import com.jawa.emailsender.exception.MailNotSentException;
import com.jawa.emailsender.exception.NoSuchApplicantNameException;

@ControllerAdvice
public class SenderControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InAppropriateTimeException.class)
	public ResponseEntity<Object> duplicateUser(InAppropriateTimeException exception) {
		return buildResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchApplicantNameException.class)
	public ResponseEntity<Object> invalidApplicantName(NoSuchApplicantNameException exception) {
		return buildResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MailNotSentException.class)
	public ResponseEntity<Object> mailNotSent(MailNotSentException exception) {
		return buildResponseEntity(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(DuplicateCompanyMailIdException.class)
	public ResponseEntity<Object> duplicateMailId(DuplicateCompanyMailIdException exception) {
		return buildResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	

	private ResponseEntity<Object> buildResponseEntity(String errorMessage, HttpStatus httpStatus) {
		return new ResponseEntity<>(errorMessage, httpStatus);
	}
	
}
