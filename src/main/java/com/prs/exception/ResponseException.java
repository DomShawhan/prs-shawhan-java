package com.prs.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;
// Custom error class to output the error to the console
public class ResponseException extends ResponseStatusException {
	static final long serialVersionUID = 43L;
	
	public ResponseException(HttpStatusCode httpStatus, String errorName, String errorMessage) {
		super(httpStatus, errorMessage);
		System.err.println(errorName + ": " + errorMessage);
	}
}
