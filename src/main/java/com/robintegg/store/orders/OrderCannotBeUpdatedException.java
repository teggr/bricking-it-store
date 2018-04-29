package com.robintegg.store.orders;

@SuppressWarnings("serial")
public class OrderCannotBeUpdatedException extends Exception {

	public OrderCannotBeUpdatedException(String reference) {
		super("Order with reference " + reference + " could not be updated");
	}

}
