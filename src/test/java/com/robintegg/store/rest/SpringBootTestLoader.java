package com.robintegg.store.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestContext;

import com.robintegg.store.BrickingItStoreApplication;
import com.robintegg.store.orders.OrderingSystem;

import cucumber.api.java8.En;

@SpringBootTest(classes = { BrickingItStoreApplication.class, TestContext.class }, webEnvironment = WebEnvironment.MOCK)
public class SpringBootTestLoader implements En {
	
	@Autowired
	private OrderingSystem orderingSystem;

	public SpringBootTestLoader() {
		Before(() -> {
			orderingSystem.clear();
		});
	}
	
}
