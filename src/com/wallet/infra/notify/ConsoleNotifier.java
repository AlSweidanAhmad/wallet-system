package com.wallet.infra.notify;

import com.wallet.app.port.Notifier;

public class ConsoleNotifier implements Notifier{

	@Override
	public void notify(String message) {
		System.out.println("[NOTIFY]" + message);
	}

}
