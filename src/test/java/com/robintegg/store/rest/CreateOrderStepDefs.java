package com.robintegg.store.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.robintegg.store.orders.NewOrder;

import cucumber.api.java8.En;

public class CreateOrderStepDefs extends RestApiStepDefs implements En {

	@Autowired
	private Customer customer;

	@Autowired
	private RestClient restClient;

	public CreateOrderStepDefs() {

		Given("^A customer wants to buy any number of bricks$", () -> {
			customer.decideOnNumberOfBricksWanted();
		});

		When("^A create Order request for a number of bricks is submitted$", () -> {
			restClient.perform(
					post("/orders").accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(toJsonContent(new NewOrder(customer.getNumberOfBricksWanted()))));
		});

		Then("^an Order reference is returned$", () -> {
			restClient.getResultActions().andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$.reference").exists())
					.andExpect(jsonPath("$.numberOfBricksWanted").value(customer.getNumberOfBricksWanted()));
		});

		Then("^the Order reference is unique to the submission$", () -> {
			restClient.getResultActions().andExpect(jsonPath("$.reference").exists())
					.andExpect(jsonPath("$.numberOfBricksWanted").value(customer.getNumberOfBricksWanted()));
		});

	}

}
