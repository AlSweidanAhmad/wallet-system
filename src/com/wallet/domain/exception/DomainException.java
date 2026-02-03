package com.wallet.domain.exception;

public class DomainException extends RuntimeException{
	protected DomainException(String message) { 
		super(message);
	}
}
