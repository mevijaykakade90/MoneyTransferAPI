package com.moneyapi.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transcationId;

	@NotNull
	@Column(name = "senderId")
	private Long senderId;

	@NotNull
	@Column(name = "receiverId")
	private Long receiverId;

	@NotNull
	@Min(value = 0, message = "balance must be positive.")
	@Column(name = "amount")
	private BigDecimal amount;

	@NotNull
	@Column(name = "status")
	private String status;

}
