package com.moneyapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moneyapi.constant.ErrorCode;
import com.moneyapi.dao.TransactionRepository;
import com.moneyapi.exception.InsufficientBalanceExceptiom;
import com.moneyapi.exception.TransactionNotExistException;
import com.moneyapi.model.Transaction;
import com.moneyapi.model.TransactionStatus;
import com.moneyapi.model.TransferRequest;
import com.moneyapi.model.User;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Transactional
	public void transferMoeny(TransferRequest request) {

		User senderUser = userService.getUserById(request.getSenderId());
		User receiverUser = userService.getUserById(request.getReceiverId());

		if (senderUser.getBalance().compareTo(request.getAmount()) < 0) {
			throw new InsufficientBalanceExceptiom(
					"Account with id:" + senderUser.getUserId() + " does not have enough balance to transfer.",
					ErrorCode.TRANSACTION_ERROR);
		}

		Transaction transaction = Transaction.builder().senderId(request.getSenderId())
				.receiverId(request.getReceiverId()).amount(request.getAmount())
				.status(TransactionStatus.PROCESSING.toString()).build();

		senderUser.setBalance(senderUser.getBalance().subtract(request.getAmount()));
		receiverUser.setBalance(receiverUser.getBalance().add(request.getAmount()));

		// update users in db
		userService.updateUser(senderUser);
		userService.updateUser(receiverUser);

		transaction.setStatus(TransactionStatus.COMPLETED.toString());

		transactionRepository.save(transaction);

	}

	@Transactional
	public Transaction transactionStatus(Long transactionId) {
		return transactionRepository.findById(transactionId)
				.orElseThrow(() -> new TransactionNotExistException(
						"Transaction with id : " + transactionId + " is not exists.", ErrorCode.TRANSACTION_ERROR,
						HttpStatus.NOT_FOUND));

	}

	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

}
