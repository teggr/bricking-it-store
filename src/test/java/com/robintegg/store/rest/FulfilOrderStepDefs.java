package com.robintegg.store.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.robintegg.store.orders.NewOrder;

import cucumber.api.java8.En;

public class FulfilOrderStepDefs extends RestApiStepDefs implements En {

	@Autowired
	private Customer customer;

	@Autowired
	private RestClient restClient;

	public FulfilOrderStepDefs() {

		Given("^an order exists$", () -> {
			customer.decideOnNumberOfBricksWanted();
			restClient.perform(
					post("/orders").accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(toJsonContent(new NewOrder(customer.getNumberOfBricksWanted()))));
			restClient.getResultActions().andDo(saveCustomerOrder(customer));
		});

		When("^a Fulfil Order request is submitted for a valid Order reference$", () -> {
			restClient.perform(post("/orders/{reference}", customer.getLastOrder().getReference())
					.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8));
		});

		Then("^the Order is marked as dispatched$", () -> {
			restClient.getResultActions().andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$.reference").value(customer.getLastOrder().getReference()))
					.andExpect(jsonPath("$.state").value(is("DISPATCHED")));
		});

		When("^a Fulfil Order request is submitted for a invalid Order reference$", () -> {
			restClient.perform(post("/orders/invalid-reference", customer.getLastOrder().getReference())
					.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8));
		});

		Then("^a 400 bad request response is returned$", () -> {
			restClient.getResultActions().andExpect(status().isBadRequest());
		});

	}

}
