package com.robintegg.store.orders;

@SuppressWarnings("serial")
public class OrderCannotBeFulfiledException extends Exception {

	public OrderCannotBeFulfiledException(String reference) {
		super("Order with reference " + reference + " could not be fulfilled");
	}

}
