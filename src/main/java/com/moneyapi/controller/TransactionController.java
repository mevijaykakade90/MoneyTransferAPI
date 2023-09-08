package com.moneyapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapi.exception.InsufficientBalanceExceptiom;
import com.moneyapi.exception.TransactionNotExistException;
import com.moneyapi.exception.UserNotExistException;
import com.moneyapi.model.Transaction;
import com.moneyapi.model.TransferRequest;
import com.moneyapi.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	/**
	 * Handles a POST request to transfer money from one account to another.
	 *
	 * @param request The TransferRequest object containing details of the
	 *                transaction.
	 * @return ResponseEntity containing a success message if the transfer is
	 *         successful, or an error message if there are any exceptions thrown.
	 */
	@PostMapping("/transferMoney")
	public ResponseEntity<?> transferMoeny(@RequestBody TransferRequest request) {
		try {

			transactionService.transferMoeny(request);

			return new ResponseEntity<>("Money sucessfully transferred.", HttpStatus.OK);
		} catch (InsufficientBalanceExceptiom e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		} catch (UserNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	/**
	 * Retrieves the status of a specific transaction based on its unique
	 * identifier.
	 *
	 * @param transactionId The unique identifier of the transaction.
	 * @return ResponseEntity containing the transaction status and HTTP status
	 *         code. If the transaction is found, it returns a ResponseEntity with
	 *         the transaction status and HttpStatus OK (200). If the transaction
	 *         does not exist, it returns a ResponseEntity with an error message and
	 *         the corresponding HttpStatus.
	 */

	@GetMapping("/transactionStatus/{transactionId}")
	public ResponseEntity<?> transactionStatus(@PathVariable Long transactionId) {
		try {

			Transaction transaction = transactionService.transactionStatus(transactionId);

			return new ResponseEntity<>(transaction, HttpStatus.OK);
		} catch (TransactionNotExistException e) {
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
	}

	/**
	 * Retrieves a list of all transactions.
	 *
	 * @return A list of Transaction objects representing all transactions.
	 */

	@GetMapping("/getAllTransactions")
	public List<Transaction> getAllTransactions() {
		return transactionService.getAllTransactions();
	}

}
