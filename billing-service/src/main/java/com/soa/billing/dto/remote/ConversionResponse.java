package com.soa.billing.dto.remote;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {
	private String from;
	private String to;
	private Double amount;
	private Double convertedAmount;
	private Double rate;
}
