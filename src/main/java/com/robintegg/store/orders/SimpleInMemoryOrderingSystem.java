package com.robintegg.store.orders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Manages the list of orders that have been created
 */
@Component
class SimpleInMemoryOrderingSystem implements OrderingSystem {

	private List<Order> orders = new ArrayList<>();

	@Override
	public Order createOrder(NewOrder newOrder) {
		Order order = Order.from(newOrder);
		orders.add(order);
		return order;
	}

	@Override
	public Order getOrder(String reference) throws OrderNotFoundException {
		return orders.stream().filter(o -> o.getReference().equals(reference)).findFirst()
				.orElseThrow(() -> new OrderNotFoundException(reference));
	}

	@Override
	public List<Order> getAllOrders() {
		return new ArrayList<>(orders);
	}

	@Override
	public void clear() {
		orders.clear();
	}

}
