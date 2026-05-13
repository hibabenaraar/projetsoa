package com.soa.billing.dto;

import com.soa.billing.dto.remote.ConversionResponse;
import com.soa.billing.dto.remote.OrderDto;
import com.soa.billing.dto.remote.ProductDto;
import com.soa.billing.dto.remote.ShippingDto;
import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class FacturationResponse {

	String invoiceReference;
	/** When this facturation was produced (always set). */
	OffsetDateTime invoiceDate;
	/** Same as {@code order.quantity} — units billed on this line. */
	int invoiceLineQuantity;
	/** Same as {@code order.price} — unit price from the order (source of truth for billing). */
	double unitPrice;
	/** {@code invoiceLineQuantity * unitPrice}. */
	double lineSubtotal;
	/**
	 * Current warehouse stock for the product line, if inventory returned a row.
	 * This is not the ordered quantity; compare with {@link #invoiceLineQuantity}.
	 */
	Integer warehouseStockQuantity;
	OrderDto order;
	/** Logistics snapshot; {@code customerName} is aligned with the order for the invoice. */
	ShippingDto shipping;
	ProductDto inventoryProduct;
	String sourceCurrency;
	String targetCurrency;
	ConversionResponse currencyConversion;
}
