package com.soa.order;

import com.soa.order.model.Order;
import com.soa.order.model.OrderStatus;
import com.soa.order.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(OrderRepository orderRepository) {
		return args -> {
			if (orderRepository.count() == 0) {
				Order order1 = new Order();
				order1.setOrderNumber("ORD-001");
				order1.setCustomerName("Amine");
				order1.setProduct("Laptop");
				order1.setQuantity(2);
				order1.setPrice(800.0);
				order1.setStatus(OrderStatus.CONFIRMED);

				Order order2 = new Order();
				order2.setOrderNumber("ORD-002");
				order2.setCustomerName("Hiba");
				order2.setProduct("Phone");
				order2.setQuantity(1);
				order2.setPrice(500.0);
				order2.setStatus(OrderStatus.PENDING);

				Order order3 = new Order();
				order3.setOrderNumber("ORD-003");
				order3.setCustomerName("Rani");
				order3.setProduct("Headphones");
				order3.setQuantity(2);
				order3.setPrice(100.0);
				order3.setStatus(OrderStatus.SHIPPED);

				Order order4 = new Order();
				order4.setOrderNumber("ORD-004");
				order4.setCustomerName("Bassem");
				order4.setProduct("Monitor");
				order4.setQuantity(1);
				order4.setPrice(300.0);
				order4.setStatus(OrderStatus.CONFIRMED);

				orderRepository.save(order1);
				orderRepository.save(order2);
				orderRepository.save(order3);
				orderRepository.save(order4);
			}
		};
	}
}
