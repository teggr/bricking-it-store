package com.robintegg.store.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.robintegg.store.orders.NewOrder;

import cucumber.api.java8.En;

public class RetrieveOrderStepDefs extends RestApiStepDefs implements En {

	@Autowired
	private Customer customer;

	@Autowired
	private RestClient restClient;

	public RetrieveOrderStepDefs() {

		Given("^a customer has submitted an order for some bricks$", () -> {
			customer.decideOnNumberOfBricksWanted();
			restClient.perform(
					post("/orders").accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(toJsonContent(new NewOrder(customer.getNumberOfBricksWanted()))));
			restClient.getResultActions().andDo(saveCustomerOrder(customer));
		});

		When("^a Get Order request is submitted with a valid Order reference$", () -> {
			OrderResource lastOrder = customer.getLastOrder();
			restClient.perform(
					get("/orders/{reference}", lastOrder.getReference()).accept(MediaType.APPLICATION_JSON_UTF8));
		});

		Then("^the order details are returned$", () -> {
			restClient.getResultActions().andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		});

		Then("^the order details contains the Order reference and the number of bricks ordered$", () -> {
			OrderResource lastOrder = customer.getLastOrder();
			restClient.getResultActions().andExpect(jsonPath("$.reference", is(lastOrder.getReference())))
					.andExpect(jsonPath("$.numberOfBricksWanted", is(lastOrder.getNumberOfBricksWanted())));
		});

		When("^a Get Order request is submitted with an invalid Order reference$", () -> {
			restClient.perform(get("/orders/invalid-value").accept(MediaType.APPLICATION_JSON_UTF8));
		});

		Then("^no order details are returned$", () -> {
			restClient.getResultActions().andExpect(status().isNotFound());
		});

		Given("^many customers have submitted orders for bricks$", () -> {
			customer.decideOnNumberOfBricksWanted();
			restClient.perform(
					post("/orders").accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(toJsonContent(new NewOrder(customer.getNumberOfBricksWanted()))));
			restClient.getResultActions().andDo(saveCustomerOrder(customer));

			customer.decideOnNumberOfBricksWanted();
			restClient.perform(
					post("/orders").accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(toJsonContent(new NewOrder(customer.getNumberOfBricksWanted()))));
			restClient.getResultActions().andDo(saveCustomerOrder(customer));
		});

		When("^a Get Orders request is submitted$", () -> {
			restClient.perform(get("/orders").accept(MediaType.APPLICATION_JSON_UTF8));
		});

		Then("^all the orders details are returned$", () -> {
			restClient.getResultActions().andExpect(jsonPath("$.*", hasSize(2)));
		});

		Then("^each order details contains the Order reference and the number of bricks ordered$", () -> {
			OrderResource firstOrder = customer.getOrders().get(0);
			OrderResource lastOrder = customer.getOrders().get(1);
			restClient.getResultActions().andExpect(jsonPath("$[0].reference", is(firstOrder.getReference())))
					.andExpect(jsonPath("$[0].numberOfBricksWanted", is(firstOrder.getNumberOfBricksWanted())))
					.andExpect(jsonPath("$[1].reference", is(lastOrder.getReference())))
					.andExpect(jsonPath("$[1].numberOfBricksWanted", is(lastOrder.getNumberOfBricksWanted())));
		});

	}

}
