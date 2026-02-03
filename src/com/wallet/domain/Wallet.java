package com.wallet.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.wallet.domain.exception.InsufficientFundsException;
import com.wallet.domain.exception.InvalidAmountException;
import com.wallet.domain.exception.WalletInactiveException;

public class Wallet {
	
    private final UUID id;
    private boolean active;
	private Money balance;
	
	private List<Transaction> transactions = new ArrayList<>();
	
	public Wallet(UUID id) {
		this.id = Objects.requireNonNull(id, "id darf nicht null sein");
		this.active = true;
		this.balance = Money.zero();
	}

	public List<Transaction> getTransactions(){
		return Collections.unmodifiableList(transactions);
	}
	
	public void deposit(Money amount) {
		ensureActive();
		ensurePositive(amount);
		
		this.balance = this.balance.add(amount);
	}
	
	public void withdraw(Money amount) {
		ensureActive();
		ensurePositive(amount);
		
		if(amount.greaterThan(this.balance)) {
			throw new InsufficientFundsException ("Nicht genügend Guthaben");
		}
		
		this.balance = this.balance.subtract(amount);
			
		
	}
	
	public void appendTransaction(Transaction tx) {
		Objects.requireNonNull(tx, "Transaction darf nicht null sein");
		
		if(!tx.getWalletId().equals(this.id)) {
			throw new IllegalArgumentException("Transaction gehört nicht zu dieser Wallet");
		}
		
		transactions.add(tx);
		
		
	}
	
	private void ensurePositive(Money amount) {
		Objects.requireNonNull(amount, "amount darf nicht null sein");
		
		if(!amount.isPositive()) throw new InvalidAmountException("Betrag muss größer als 0 sein");	
	}
	
	private void ensureActive() {
		
		if(!active)throw new WalletInactiveException("Wallet ist inaktiv");
	}
	
	public UUID getId() { return id; }
	public Money getBalance() { return balance; }
	public boolean isActive() { return active; }
	public boolean deactivate() { return this.active = false; }
	public boolean activate() { return this.active = true; }

	
	
	
}
