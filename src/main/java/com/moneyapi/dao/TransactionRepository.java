package com.moneyapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
