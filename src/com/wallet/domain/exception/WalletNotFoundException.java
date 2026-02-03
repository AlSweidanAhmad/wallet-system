package com.wallet.domain.exception;

public class WalletNotFoundException extends DomainException{
	public WalletNotFoundException(String message) {
		super(message);
	}
}
