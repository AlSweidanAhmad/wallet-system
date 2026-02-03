package com.wallet.app.port;

import java.util.Optional;
import java.util.UUID;

import com.wallet.domain.Wallet;

public interface WalletRepository {
	void save(Wallet wallet);
	Optional<Wallet> findById(UUID walletId);
}
