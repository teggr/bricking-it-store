package com.robintegg.store.orders;

import java.util.List;

public interface OrderingSystem {

	/**
	 * Create an {@link Order}
	 */
	Order createOrder(NewOrder newOrder);

	/**
	 * Get an {@link Order} that has been created
	 */
	Order getOrder(String reference) throws OrderNotFoundException;

	/**
	 * Get all {@link Order}s that have been created
	 */
	List<Order> getAllOrders();

	/**
	 * Clear all the {@link Order}s
	 */
	void clear();

}
