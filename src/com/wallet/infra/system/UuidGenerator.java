package com.wallet.infra.system;

import java.util.UUID;

import com.wallet.app.port.IdGenerator;

public class UuidGenerator implements IdGenerator{

	@Override
	public UUID newId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID();
	}

}
