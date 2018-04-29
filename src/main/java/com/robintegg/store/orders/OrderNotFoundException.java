package com.robintegg.store.orders;

@SuppressWarnings("serial")
public class OrderNotFoundException extends Exception {

	public OrderNotFoundException(String reference) {
		super("Could not find order for reference " + reference);
	}

}
