package com.moneyapi.service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class WithdrawalServiceStub implements WithdrawalService {

	private final ConcurrentMap<WithdrawalId, Withdrawal> requests = new ConcurrentHashMap<>();

	@Override
	public void requestWithdrawal(WithdrawalId id, Address address, BigDecimal amount) {

		final var existing = requests.putIfAbsent(id, new Withdrawal(finalState(), finaliseAt(), address, amount));
		
		if (existing != null && !Objects.equals(existing.address(), address)
				&& !Objects.equals(existing.amount(), amount))
			throw new IllegalStateException("Withdrawal request with id[%s] is already present".formatted(id));

	}

	private WithdrawalState finalState() {
		return ThreadLocalRandom.current().nextBoolean() ? WithdrawalState.COMPLETED : WithdrawalState.FAILED;
	}

	private long finaliseAt() {
		return System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(1000, 10000);
	}

	@Override
	public WithdrawalState getRequestState(WithdrawalId id) {
		final var request = requests.get(id);
		if (request == null)
			throw new IllegalArgumentException("Request %s is not found".formatted(id));
		return request.finalState();
	}

	record Withdrawal(WithdrawalState state, long finaliseAt, Address address, BigDecimal amount) {
		public WithdrawalState finalState() {
			return finaliseAt <= System.currentTimeMillis() ? state : WithdrawalState.PROCESSING;
		}
	}
}