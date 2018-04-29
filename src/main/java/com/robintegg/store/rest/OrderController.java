package com.robintegg.store.rest;

import java.util.List;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.robintegg.store.orders.NewOrder;
import com.robintegg.store.orders.Order;
import com.robintegg.store.orders.OrderNotFoundException;
import com.robintegg.store.orders.OrderingSystem;

@RestController
@ExposesResourceFor(Order.class)
@RequestMapping(path = "/orders")
class OrderController {

	private final OrderResourceAssembler assembler;
	private final OrderingSystem orderingSystem;

	public OrderController(OrderingSystem orderingSystem, OrderResourceAssembler assembler) {
		this.orderingSystem = orderingSystem;
		this.assembler = assembler;
	}

	@PostMapping
	public OrderResource postNewOrder(@RequestBody NewOrder newOrder) {
		return assembler.toResource(orderingSystem.createOrder(newOrder));
	}

	@GetMapping
	public List<OrderResource> get() {
		return assembler.toResources(orderingSystem.getAllOrders());
	}

	@GetMapping(path = "/{reference}")
	public OrderResource getOrder(@PathVariable("reference") String reference) throws OrderNotFoundException {
		return assembler.toResource(orderingSystem.getOrder(reference));
	}

	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleOrderNotFoundException() {

	}

}
