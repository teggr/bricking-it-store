package com.robintegg.store.orders;

import java.util.UUID;

import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link Order} accepted by the {@link OrderingSystem} and contains
 * {@link State} of the order
 */
public class Order implements Identifiable<String> {

	public enum State {
		STARTED;
	}

	private int numberOfBricksWanted;

	@JsonIgnore
	private State state;

	@JsonInclude(Include.NON_NULL)
	private String reference;

	@JsonCreator
	public Order(@JsonProperty("numberOfBricksWanted") int numberOfBricksWanted) {
		this.numberOfBricksWanted = numberOfBricksWanted;
		this.state = State.STARTED;
		this.reference = UUID.randomUUID().toString();
	}

	public String getReference() {
		return reference;
	}

	public int getNumberOfBricksWanted() {
		return numberOfBricksWanted;
	}

	public State getState() {
		return state;
	}

	@Override
	public String getId() {
		return getReference();
	}

	public static Order from(NewOrder newOrder) {
		return new Order(newOrder.getNumberOfBricksWanted());
	}

	public void update(OrderUpdate orderUpdate) {
		numberOfBricksWanted = orderUpdate.getNumberOfBricksWanted();
	}

}
