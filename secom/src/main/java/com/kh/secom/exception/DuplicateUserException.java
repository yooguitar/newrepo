package com.kh.secom.exception;

public class DuplicateUserException extends RuntimeException {
	
	public DuplicateUserException(String message) {
		super(message);
	}
}
