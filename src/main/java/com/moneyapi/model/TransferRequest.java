package com.moneyapi.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransferRequest {

	@NotNull
	private Long senderId;

	@NotNull
	private Long receiverId;

	@NotNull
	@Min(value = 0, message = "Transfer amount can not be less than zero")
	private BigDecimal amount;

	@JsonCreator
	public TransferRequest(@NotNull @JsonProperty("senderId") Long senderId,
			@NotNull @JsonProperty("receiverId") Long receiverId,
			@NotNull @Min(value = 0, message = "Transfer amount can not be less than zero") @JsonProperty("amount") BigDecimal amount) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.amount = amount;
	}

	@JsonCreator
	public TransferRequest() {
		super();
	}

}
