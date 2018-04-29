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

	@Override
	public Order updateOrder(String reference, OrderUpdate orderUpdate) throws OrderCannotBeUpdatedException {
		try {
			Order order = getOrder(reference);
			order.update(orderUpdate);
			return order;
		} catch (OrderNotFoundException e) {
			throw new OrderCannotBeUpdatedException(reference);
		}
	}

	@Override
	public Order fulfilOrder(String reference, FulfilOrder fulfilOrder) throws OrderCannotBeFulfiledException {
		try {
			Order order = getOrder(reference);
			order.fulfil(fulfilOrder);
			return order;
		} catch (OrderNotFoundException e) {
			throw new OrderCannotBeFulfiledException(reference);
		}
	}

}
