package com.moneyapi.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moneyapi.constant.ErrorCode;
import com.moneyapi.dao.ExternalTransactionRepository;
import com.moneyapi.exception.InsufficientBalanceExceptiom;
import com.moneyapi.exception.TransactionNotExistException;
import com.moneyapi.model.ExternalTransaction;
import com.moneyapi.model.ExternalTransferRequest;
import com.moneyapi.model.User;
import com.moneyapi.service.WithdrawalService.Address;
import com.moneyapi.service.WithdrawalService.WithdrawalState;

import jakarta.transaction.Transactional;

@Service
public class ExternalTransactionService {

	@Autowired
	private UserService userService;

	@Autowired
	private ExternalTransactionRepository externalTransactionRepository;

	@Autowired
	private WithdrawalService withdrawalService;

	private WithdrawalState status;

	@Transactional
	public void externalTransferMoeny(ExternalTransferRequest request) {

		User senderUser = userService.getUserById(request.getSenderId());

		if (senderUser.getBalance().compareTo(request.getAmount()) < 0) {
			throw new InsufficientBalanceExceptiom(
					"Account with id:" + senderUser.getUserId() + " does not have enough balance to transfer.",
					ErrorCode.TRANSACTION_ERROR);
		}

		// External address logic.
		WithdrawalService.WithdrawalId id = new WithdrawalService.WithdrawalId(UUID.randomUUID());
		withdrawalService.requestWithdrawal(id, new Address(request.getAddress()), request.getAmount());
		status = withdrawalService.getRequestState(id);

		ExternalTransaction transaction = ExternalTransaction.builder().senderId(request.getSenderId())
				.address(request.getAddress().toString()).amount(request.getAmount()).status(status.name()).build();

		senderUser.setBalance(senderUser.getBalance().subtract(request.getAmount()));

		// update sender user after changing status
		ExternalTransaction savedTransaction = externalTransactionRepository.save(transaction);
		userService.updateUser(senderUser);

		// get genrated id
		Long generatedId = savedTransaction.getTranscationId();
		transaction.setTranscationId(generatedId);
		transaction.setStatus(status.name());

		if (status.compareTo(WithdrawalState.PROCESSING) == 0) {
			ExecutorService service = Executors.newSingleThreadExecutor();
			service.execute(() -> {
				while (status.compareTo(WithdrawalState.PROCESSING) != 0) {

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					status = ThreadLocalRandom.current().nextBoolean() ? WithdrawalState.COMPLETED
							: WithdrawalState.FAILED;

					// if transaction is failed the update transaction and reverse money to the
					// customer / user
					if (status.compareTo(WithdrawalState.FAILED) == 0) {

						senderUser.setBalance(senderUser.getBalance().add(request.getAmount()));
						userService.updateUser(senderUser);

					}
					transaction.setStatus(status.name());
					externalTransactionRepository.save(transaction);

				}
			});
			service.shutdown();
		}

	}

	@Transactional
	public ExternalTransaction transactionStatus(Long transactionId) {
		return externalTransactionRepository.findById(transactionId)
				.orElseThrow(() -> new TransactionNotExistException("Transaction with id : " + transactionId + " is not exists.",
						ErrorCode.TRANSACTION_ERROR, HttpStatus.NOT_FOUND));

	}

	public List<ExternalTransaction> getAllTransactions() {
		return externalTransactionRepository.findAll();
	}

}
