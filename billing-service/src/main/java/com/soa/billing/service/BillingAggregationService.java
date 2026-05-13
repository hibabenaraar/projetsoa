package com.soa.billing.service;

import com.soa.billing.client.CurrencyFeignClient;
import com.soa.billing.client.InventoryFeignClient;
import com.soa.billing.client.OrderFeignClient;
import com.soa.billing.client.ShippingFeignClient;
import com.soa.billing.dto.FacturationResponse;
import com.soa.billing.dto.remote.ConversionRequest;
import com.soa.billing.dto.remote.ConversionResponse;
import com.soa.billing.dto.remote.OrderDto;
import com.soa.billing.dto.remote.ProductDto;
import com.soa.billing.dto.remote.ShippingDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class BillingAggregationService {

	private static final String DEFAULT_SOURCE_CURRENCY = "USD";

	private final OrderFeignClient orderFeignClient;
	private final ShippingFeignClient shippingFeignClient;
	private final InventoryFeignClient inventoryFeignClient;
	private final CurrencyFeignClient currencyFeignClient;

	public FacturationResponse buildFacturation(Long orderId, String targetCurrency) {
		OrderDto order = orderFeignClient.getOrderById(orderId);
		int lineQty = order.getQuantity();
		double unitPrice = order.getPrice();
		double lineSubtotal = lineQty * unitPrice;

		ShippingDto shipping = alignShippingForInvoice(order, safeShipping(orderId));
		ProductDto product = safeProduct(order.getProduct());
		Integer warehouseStock = product != null ? product.getQuantity() : null;

		String source = DEFAULT_SOURCE_CURRENCY;
		String target = targetCurrency != null && !targetCurrency.isBlank()
				? targetCurrency.toUpperCase()
				: source;

		ConversionResponse conversion = null;
		if (!source.equalsIgnoreCase(target)) {
			conversion = safeConvert(new ConversionRequest(source, target, lineSubtotal));
		}

		String invoiceReference = "INV-" + order.getOrderNumber();

		return FacturationResponse.builder()
				.invoiceReference(invoiceReference)
				.invoiceDate(OffsetDateTime.now())
				.invoiceLineQuantity(lineQty)
				.unitPrice(unitPrice)
				.lineSubtotal(lineSubtotal)
				.warehouseStockQuantity(warehouseStock)
				.order(order)
				.shipping(shipping)
				.inventoryProduct(product)
				.sourceCurrency(source)
				.targetCurrency(target)
				.currencyConversion(conversion)
				.build();
	}

	/**
	 * Invoice party name must match the order; shipping service may store logistics contact only.
	 */
	private ShippingDto alignShippingForInvoice(OrderDto order, ShippingDto raw) {
		if (raw == null) {
			return null;
		}
		return new ShippingDto(
				raw.getId(),
				raw.getOrderId(),
				order.getCustomerName(),
				raw.getAddress(),
				raw.getCity(),
				raw.getPhone(),
				raw.getStatus(),
				raw.getShippingDate(),
				raw.getDeliveryDate());
	}

	private ShippingDto safeShipping(Long orderId) {
		try {
			return shippingFeignClient.getShippingByOrderId(orderId);
		} catch (FeignException e) {
			if (e.status() == 404) {
				return null;
			}
			throw e;
		}
	}

	private ProductDto safeProduct(String productName) {
		try {
			return inventoryFeignClient.getProductByName(productName);
		} catch (FeignException e) {
			if (e.status() == 404) {
				return null;
			}
			throw e;
		}
	}

	private ConversionResponse safeConvert(ConversionRequest request) {
		try {
			return currencyFeignClient.convert(request);
		} catch (FeignException e) {
			return null;
		}
	}
}
