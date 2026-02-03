package com.wallet.app.port;

import com.wallet.domain.Transaction;

public interface TransactionRepository {
	void save(Transaction tx);
}
