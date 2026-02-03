package com.wallet.infra.system;

import java.time.Instant;

import com.wallet.app.port.TimeProvider;

public class SystemTimeProvider  implements TimeProvider{

	@Override
	public Instant now() {
		return Instant.now();
	}

}
