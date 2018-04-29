package com.robintegg.store.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Details for a new {@link Order} to be created in the {@link OrderingSystem}
 */
public class NewOrder {

	private int numberOfBricksWanted;

	@JsonCreator
	public NewOrder(@JsonProperty("numberOfBricksWanted") int numberOfBricksWanted) {
		this.numberOfBricksWanted = numberOfBricksWanted;
	}

	public int getNumberOfBricksWanted() {
		return numberOfBricksWanted;
	}

}
