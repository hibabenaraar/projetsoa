package com.soa.billing.dto.remote;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingDto {
	private Long id;
	private Long orderId;
	private String customerName;
	private String address;
	private String city;
	private String phone;
	private String status;
	private LocalDate shippingDate;
	private LocalDate deliveryDate;
}
