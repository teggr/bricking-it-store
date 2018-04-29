package com.robintegg.store.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.robintegg.store.orders.NewOrder;
import com.robintegg.store.orders.OrderUpdate;

import cucumber.api.java8.En;

public class UpdateOrderStepDefs extends RestApiStepDefs implements En {

	@Autowired
	private Customer customer;

	@Autowired
	private RestClient restClient;

	public UpdateOrderStepDefs() {

		Given("^a customer has ordered a number of bricks$", () -> {
			customer.decideOnNumberOfBricksWanted();
			restClient.perform(
					post("/orders").accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(toJsonContent(new NewOrder(customer.getNumberOfBricksWanted()))));
			restClient.getResultActions().andDo(saveCustomerOrder(customer));
		});

		When("^an Update Order request for an existing order reference and a number of bricks is submitted$", () -> {
			customer.decideOnNumberOfBricksWanted();
			restClient.perform(post("/orders/{reference}", customer.getLastOrder().getReference())
					.accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(toJsonContent(new OrderUpdate(customer.getNumberOfBricksWanted()))));
			restClient.getResultActions().andDo(saveCustomerOrder(customer));
		});

		Then("^an Order reference the returned$", () -> {
			restClient.getResultActions().andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$.reference").value(customer.getFirstOrder().getReference()));
		});

	}

}
