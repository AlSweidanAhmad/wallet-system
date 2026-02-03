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

public class TransferUseCase {
	private final WalletRepository walletRepository;
	private final TransactionRepository transactionRepository;
	private final Notifier notifier;
	private final IdGenerator idGenerator;
	private final TimeProvider timeProvider;
	public TransferUseCase(WalletRepository walletRepository,
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
	
	public void transfer(UUID fromWalletId, UUID toWalletId, BigDecimal amount) {
		if(fromWalletId.equals(toWalletId)) {
			throw new IllegalArgumentException("fromWalletId und toWalletId dürfen nicht gleich sein");
		}
		
		Wallet from = walletRepository.findById(fromWalletId)
				.orElseThrow(() -> new WalletNotFoundException("Sender-Wallet nicht gefunden: " + fromWalletId));
		
		Wallet to = walletRepository.findById(toWalletId)
				.orElseThrow(() -> new WalletNotFoundException("Sender-Wallet nicht gefunden: " + toWalletId));
		
		Money money = Money.of(amount);
		
		from.withdraw(money);
		to.deposit(money);
		
		Transaction outTx= new Transaction(idGenerator.newId(),
				fromWalletId,
				money,
				timeProvider.now(),
				TransactionType.TRANSFER_OUT
		);
		
		Transaction inTx= new Transaction(idGenerator.newId(),
				toWalletId,
				money,
				timeProvider.now(),
				TransactionType.TRANSFER_IN
		);
		
		from.appendTransaction(outTx);
		to.appendTransaction(inTx);
		
		walletRepository.save(from);
		walletRepository.save(to);
		transactionRepository.save(inTx);
		transactionRepository.save(outTx);
		
		notifier.notify("Transfer OK: from=" +fromWalletId +"to=" + toWalletId + " amount=" + money);
	}
	
	
}
