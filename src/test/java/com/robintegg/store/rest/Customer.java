package com.robintegg.store.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("cucumber-glue")
@Component
public class Customer {

	private Random randomNumberGenerator = new Random();

	private int numberOfBricksWanted;

	private List<OrderResource> orders = new ArrayList<>();

	public void decideOnNumberOfBricksWanted() {
		numberOfBricksWanted = randomNumberGenerator.nextInt(100);
	}

	public int getNumberOfBricksWanted() {
		return numberOfBricksWanted;
	}

	public void addOrder(OrderResource order) {
		this.orders.add(order);
	}

	public OrderResource getLastOrder() {
		return orders.get(orders.size() - 1);
	}
	
	public List<OrderResource> getOrders() {
		return orders;
	}

	public OrderResource getFirstOrder() {
		return orders.get(0);
	}

}
