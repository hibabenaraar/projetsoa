package com.example.shippingService;

import com.example.shippingService.entity.Shipping;
import com.example.shippingService.repository.ShippingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@EnableDiscoveryClient
@SpringBootApplication
public class ShippingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedShipping(ShippingRepository shippingRepository) {
		return args -> {
			if (shippingRepository.count() == 0) {
				LocalDate today = LocalDate.now();
				save(shippingRepository, 1L, "Amine", "1 Av. Habib Bourguiba", "Tunis", "+21611111111", "PENDING", null, null);
				save(shippingRepository, 2L, "Hiba", "12 Rue de la Paix", "Sfax", "+21622222222", "PENDING", null, null);
				save(shippingRepository, 3L, "Rani", "3 Zone Industrielle", "Sousse", "+21633333333", "SHIPPED", today.minusDays(3), null);
				save(shippingRepository, 4L, "Bassem", "8 Lac Nord", "Tunis", "+21644444444", "PENDING", null, null);
			}
		};
	}

	private static void save(
			ShippingRepository repo,
			long orderId,
			String customerName,
			String address,
			String city,
			String phone,
			String status,
			LocalDate shippingDate,
			LocalDate deliveryDate) {
		Shipping s = new Shipping();
		s.setOrderId(orderId);
		s.setCustomerName(customerName);
		s.setAddress(address);
		s.setCity(city);
		s.setPhone(phone);
		s.setStatus(status);
		s.setShippingDate(shippingDate);
		s.setDeliveryDate(deliveryDate);
		repo.save(s);
	}
}
