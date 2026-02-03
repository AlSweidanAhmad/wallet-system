package com.wallet.app.port;

import java.time.Instant;

public interface TimeProvider {
	Instant now();
}
