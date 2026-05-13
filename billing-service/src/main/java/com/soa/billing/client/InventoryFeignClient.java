package com.soa.billing.client;

import com.soa.billing.dto.remote.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service", contextId = "inventoryClient")
public interface InventoryFeignClient {

	@GetMapping("/inventory/product/{productName}")
	ProductDto getProductByName(@PathVariable("productName") String productName);
}
