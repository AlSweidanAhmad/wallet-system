package com.wallet.app.usecase;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import com.wallet.app.port.IdGenerator;
import com.wallet.app.port.Notifier;
import com.wallet.app.port.TimeProvider;
import com.wallet.app.port.TransactionRepository;
import com.wallet.app.port.WalletRepository;
import com.wallet.domain.Money;
import com.wallet.domain.Transaction;
import com.wallet.domain.TransactionType;
import com.wallet.domain.Wallet;
import com.wallet.domain.exception.WalletNotFoundException;

public class DepositUseCase {
	private final WalletRepository walletRepository;
	private final TransactionRepository transactionRepository;
	private final Notifier notifier;
	private final IdGenerator idGenerator;
	private final TimeProvider timeProvider;
	
	public DepositUseCase(
			WalletRepository walletRepository,
			TransactionRepository transactionRepository,
			Notifier notifier,
			IdGenerator idGenerator,
			TimeProvider timeProvider
			) {
		
		this.walletRepository = Objects.requireNonNull(walletRepository);
		this.transactionRepository = Objects.requireNonNull(transactionRepository);
		this.notifier = Objects.requireNonNull(notifier);
		this.idGenerator = Objects.requireNonNull(idGenerator);
		this.timeProvider = Objects.requireNonNull(timeProvider);
	}
	
	public void deposit(UUID walletId, BigDecimal amount) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(() -> new WalletNotFoundException("Wallet nicht gefunden" + walletId));
		
		Money money = Money.of(amount);
		
		wallet.deposit(money);
		
		Transaction tx= new Transaction(idGenerator.newId(),
													walletId,
													money,
													timeProvider.now(),
													TransactionType.DEPOSIT
													);
	wallet.appendTransaction(tx);
	
	walletRepository.save(wallet);
	transactionRepository.save(tx);
	
	notifier.notify("Depostit OK: wallet=" +walletId + ",amount=" + money);
	}
	
	
}
