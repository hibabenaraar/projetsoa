package com.soa.billing.controller;

import com.soa.billing.dto.FacturationResponse;
import com.soa.billing.service.BillingAggregationService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BillingController {

	private final BillingAggregationService billingAggregationService;

	@GetMapping("/facturation/{orderId}")
	public ResponseEntity<FacturationResponse> getFacturation(
			@PathVariable Long orderId,
			@RequestParam(required = false) String targetCurrency) {
		try {
			return ResponseEntity.ok(billingAggregationService.buildFacturation(orderId, targetCurrency));
		} catch (FeignException e) {
			if (e.status() == 404) {
				return ResponseEntity.notFound().build();
			}
			throw e;
		}
	}
}
