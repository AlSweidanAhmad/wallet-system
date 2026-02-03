package com.wallet.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {
	public static final int SCALE = 2;
	public static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
	
	private final BigDecimal value;
	
	private Money(BigDecimal value) {
		this.value = value.setScale(SCALE, ROUNDING);
	}
	
	public static Money of(BigDecimal amount) {
		Objects.requireNonNull(amount,"amount darf nicht null sein");
		return new Money(amount);
	}
	
	public static Money zero() {
		return new Money(BigDecimal.ZERO);
	}

	public BigDecimal asBigDecimal() {
		return value;
	}
	
	public boolean isPositive() {
		return value.compareTo(BigDecimal.ZERO) > 0;
	}
	
	public Money add(Money other) {
		Objects.requireNonNull(other,"other darf nicht null sein");
		
		return new Money(this.value.add(other.value));
	}
	
	public Money subtract(Money other) {
		Objects.requireNonNull(other,"other darf nicht null sein");
		
		return new Money(this.value.subtract(other.value));
	}
	
	public boolean greaterThan(Money other) {
		Objects.requireNonNull(other,"other darf nicht null sein");
		return this.value.compareTo(other.value) > 0;
	}
	
	@Override
	public String toString() {
		return value.toPlainString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Money m)) return false;
		
		return this.value.compareTo(m.value) == 0;
	}
	
	@Override
	public int hashCode() {
		return value.stripTrailingZeros().hashCode();
	}
}
