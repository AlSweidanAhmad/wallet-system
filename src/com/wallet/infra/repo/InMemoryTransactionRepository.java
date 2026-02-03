package com.wallet.infra.repo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import com.wallet.app.port.TransactionRepository;
import com.wallet.domain.Transaction;

public class InMemoryTransactionRepository implements TransactionRepository{
	private final List<Transaction> store = new CopyOnWriteArrayList<>();
	
	@Override
	public void save(Transaction tx) {
		Objects.requireNonNull(tx,"tx darf nicht null sein");
		store.add(tx);
	}
	
	public List<Transaction> all() {
		return Collections.unmodifiableList(store);
	}
}
