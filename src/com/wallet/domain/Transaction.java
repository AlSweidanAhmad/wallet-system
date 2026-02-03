package com.wallet.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {

	private final UUID id; 
	private final UUID walletId; 
	private final Money amount;
	private final Instant timestamp; 
	private final TransactionType type;
	
	
	
	public Transaction(UUID id, UUID walletId, Money amount, Instant timestamp, TransactionType type) {
		this.id = Objects.requireNonNull(id, "id darf nicht null sein");
		this.walletId = Objects.requireNonNull(walletId, "walletId darf nicht null sein");
		this.amount = Objects.requireNonNull(amount,"amount darf nicht null sein");
		this.timestamp = Objects.requireNonNull(timestamp, "timeStemp darf nicht null sein");
		this.type = Objects.requireNonNull(type,"type darf nicht null sein");
	}
	public UUID getId() { return id; }
	public UUID getWalletId() { return walletId; }
	public Instant getTimestamp() { return timestamp; }
	public TransactionType getType() { return type; }
	public Money getAmount() { return amount; }
	
	
}
