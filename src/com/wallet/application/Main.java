package com.wallet.application;

import java.math.BigDecimal;
import java.util.UUID;

import com.wallet.app.usecase.CreateWalletUseCase;
import com.wallet.app.usecase.DepositUseCase;
import com.wallet.app.usecase.TransferUseCase;
import com.wallet.app.usecase.WithdrawUseCase;
import com.wallet.infra.notify.ConsoleNotifier;
import com.wallet.infra.repo.InMemoryTransactionRepository;
import com.wallet.infra.repo.InMemoryWalletRepository;
import com.wallet.infra.system.SystemTimeProvider;
import com.wallet.infra.system.UuidGenerator;

public class Main {
	 public static void main(String[] args) {
		var txRepo= new InMemoryTransactionRepository();
		var walletRepo = new InMemoryWalletRepository();
		var notifier = new ConsoleNotifier();
		var idGen = new UuidGenerator();
		var time = new SystemTimeProvider();
	
		var create = new CreateWalletUseCase(walletRepo, idGen);
		var deposit = new DepositUseCase(walletRepo, txRepo, notifier, idGen, time);
		var withdraw = new WithdrawUseCase(walletRepo, txRepo, notifier, idGen, time);
		var transfer = new TransferUseCase(walletRepo, txRepo, notifier, idGen, time);
		
		var ahmadWallet = create.create();
		var moradWallet = create.create();
		
		deposit.deposit(ahmadWallet, new BigDecimal("100.00"));
		withdraw.withdraw(ahmadWallet, new BigDecimal("20.00"));
		transfer.transfer(ahmadWallet, moradWallet, new BigDecimal("30.00"));
	 	System.out.println("Wallet1 balance" + walletRepo.findById(ahmadWallet).get().getBalance());
	 	System.out.println("Wallet2 balance" + walletRepo.findById(moradWallet).get().getBalance());
	 }
}
