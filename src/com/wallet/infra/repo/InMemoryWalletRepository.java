package com.wallet.infra.repo;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.wallet.app.port.WalletRepository;
import com.wallet.domain.Wallet;

public class InMemoryWalletRepository implements WalletRepository{

	private final Map<UUID, Wallet> store = new ConcurrentHashMap<>();
	
	@Override
	public void save(Wallet wallet) {
		Objects.requireNonNull(wallet, "wallet darf nicht null sein");
		store.put(wallet.getId(), wallet);
	}

	@Override
	public Optional<Wallet> findById(UUID walletId) {
		Objects.requireNonNull(walletId, "walletId darf nicht null sein");
		return Optional.ofNullable(store.get(walletId));
	}

}
