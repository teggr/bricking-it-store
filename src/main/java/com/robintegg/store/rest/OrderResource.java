package com.robintegg.store.rest;

import org.springframework.hateoas.ResourceSupport;

class OrderResource extends ResourceSupport {

	int numberOfBricksWanted;
	String reference;
	String state;

	public int getNumberOfBricksWanted() {
		return numberOfBricksWanted;
	}

	public String getReference() {
		return reference;
	}
	
	public String getState() {
		return state;
	}

}
