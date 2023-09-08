package com.moneyapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.model.ExternalTransaction;

public interface ExternalTransactionRepository extends JpaRepository<ExternalTransaction, Long> {

}
