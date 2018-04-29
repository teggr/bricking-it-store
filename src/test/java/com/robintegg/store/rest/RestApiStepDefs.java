package com.robintegg.store.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class RestApiStepDefs {

	@Autowired
	private ObjectMapper objectMapper;

	protected byte[] toJsonContent(Object o) throws Exception {
		return objectMapper.writeValueAsBytes(o);
	}

	protected <T> T toObject(byte[] contentAsByteArray, Class<T> valueType) throws Exception {
		return objectMapper.readValue(contentAsByteArray, valueType);
	}

	protected CustomerResultHandler saveCustomerOrder(Customer customer) {
		return new CustomerResultHandler(customer);
	}

	protected class CustomerResultHandler implements ResultHandler {

		private Customer customer;

		public CustomerResultHandler(Customer customer) {
			this.customer = customer;
		}

		@Override
		public void handle(MvcResult result) throws Exception {
			customer.addOrder(
					toObject(result.getResponse().getContentAsByteArray(),
							OrderResource.class));
		}

	}

}
