package com.robintegg.store.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderUpdate {

	private int numberOfBricksWanted;

	@JsonCreator
	public OrderUpdate(@JsonProperty("numberOfBricksWanted") int numberOfBricksWanted) {
		this.numberOfBricksWanted = numberOfBricksWanted;
	}

	public int getNumberOfBricksWanted() {
		return numberOfBricksWanted;
	}
	
}
