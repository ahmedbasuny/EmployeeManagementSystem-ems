package com.peopleflow.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeDataNotValidException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmployeeDataNotValidException() {
		super("Employee data is not valid. Please make sure all required data are provided.");
	}
}
