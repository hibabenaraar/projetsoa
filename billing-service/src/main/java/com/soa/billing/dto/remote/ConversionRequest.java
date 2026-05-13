package com.soa.billing.dto.remote;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRequest {
	private String from;
	private String to;
	private Double amount;
}
