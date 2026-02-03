package com.wallet.app.usecase;

import java.util.Objects;
import java.util.UUID;

import com.wallet.app.port.IdGenerator;
import com.wallet.app.port.WalletRepository;
import com.wallet.domain.Wallet;

public class CreateWalletUseCase {
	
	private final WalletRepository walletRepository;
	private final IdGenerator idGenerator;
	
	public CreateWalletUseCase(WalletRepository walletRepository, IdGenerator idGenerator) {
		this.walletRepository = Objects.requireNonNull(walletRepository, "walletRepository null");
		this.idGenerator = Objects.requireNonNull(idGenerator, "idGenerator null");
	}
	
	public UUID create() {
		UUID walletId = idGenerator.newId();
		Wallet wallet = new Wallet(walletId);
		walletRepository.save(wallet);
		return walletId;
	}
}
