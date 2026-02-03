package com.wallet.domain.exception;

public class InvalidAmountException extends DomainException{
	public InvalidAmountException(String message) {
		super(message);
	}
}
