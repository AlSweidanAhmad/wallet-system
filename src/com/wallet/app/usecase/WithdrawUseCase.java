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

public class WithdrawUseCase {
	private final WalletRepository walletRepository;
	private final TransactionRepository transactionRepository;
	private final Notifier notifier;
	private final IdGenerator idGenerator;
	private final TimeProvider timeProvider;
	
	public WithdrawUseCase(WalletRepository walletRepository,
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
	
	public void withdraw(UUID walletId, BigDecimal amount) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(() -> new WalletNotFoundException("Wallet nicht gefunden" + walletId));
		
		Money money = Money.of(amount);
		wallet.withdraw(money);
		
		Transaction tx = new Transaction(
				idGenerator.newId(),
				walletId,
				money,
				timeProvider.now(),
				TransactionType.WITHDRAW);
		
		wallet.appendTransaction(tx);
		
		walletRepository.save(wallet);
		transactionRepository.save(tx);
	
		notifier.notify("Withdraw OK: wallet=" + walletId + ", amount=" + money);
	}
	
	
	
	
}
