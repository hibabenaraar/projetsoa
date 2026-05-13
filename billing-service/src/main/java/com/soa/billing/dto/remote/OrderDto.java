package com.soa.billing.dto.remote;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	private Long id;
	private String orderNumber;
	private String customerName;
	private String product;
	private Integer quantity;
	private Double price;
	private OrderStatusDto status;
}
