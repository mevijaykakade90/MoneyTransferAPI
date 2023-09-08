package com.moneyapi.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExternalTransferRequest {

	@NotNull
	private Long senderId;

	@NotNull
	private String address;

	@NotNull
	@Min(value = 0, message = "Transfer amount can not be less than zero")
	private BigDecimal amount;

	@JsonCreator
	public ExternalTransferRequest(@NotNull @JsonProperty("senderId") Long senderId,
			@NotNull @JsonProperty("address") @NotNull String address,
			@NotNull @Min(value = 0, message = "Transfer amount can not be less than zero") @JsonProperty("amount") BigDecimal amount) {
		super();
		this.senderId = senderId;
		this.address = address;
		this.amount = amount;
	}

	@JsonCreator
	public ExternalTransferRequest() {
		super();
	}

}
