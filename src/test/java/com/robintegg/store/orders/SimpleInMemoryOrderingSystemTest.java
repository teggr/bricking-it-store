package com.robintegg.store.orders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.robintegg.store.orders.Order.State;

public class SimpleInMemoryOrderingSystemTest {

	private SimpleInMemoryOrderingSystem orderingSystem = new SimpleInMemoryOrderingSystem();

	@Test
	public void shouldCreateOrderForNumberOfBricks() {

		// given
		NewOrder newOrder = new NewOrder(5);

		// when
		Order order = orderingSystem.createOrder(newOrder);

		// then
		assertThat(order.getNumberOfBricksWanted(), is(5));
		assertThat(order.getReference(), notNullValue());
		assertThat(order.getState(), is(State.OPEN));

	}

	@Test
	public void shouldCreateUniqueReferenceNumbers() {

		// given
		NewOrder newOrder1 = new NewOrder(5);
		NewOrder newOrder2 = new NewOrder(5);

		// when
		Order order1 = orderingSystem.createOrder(newOrder1);
		Order order2 = orderingSystem.createOrder(newOrder2);

		// then
		assertThat(order1.getReference(), not(equalToIgnoringCase(order2.getReference())));

	}

	@Test
	public void shouldFindOrderThatHasBeenCreated() throws OrderNotFoundException {

		// given
		NewOrder newOrder = new NewOrder(5);
		Order order = orderingSystem.createOrder(newOrder);

		// when
		Order order2 = orderingSystem.getOrder(order.getReference());

		// then
		assertThat(order2.getReference(), is(order.getReference()));
		assertThat(order2.getNumberOfBricksWanted(), is(order.getNumberOfBricksWanted()));
		assertThat(order2.getState(), is(order.getState()));

	}

	@Test(expected = OrderNotFoundException.class)
	public void shouldNotFindOrderThatHasNotBeenCreated() throws OrderNotFoundException {

		// given

		try {
			// when
			orderingSystem.getOrder("invalid");
		} catch (OrderNotFoundException e) {
			assertThat(e.getMessage(), is("Could not find order for reference invalid"));
			throw e;
		}

	}

	@Test
	public void shouldHaveNoOrdersToBeginWith() throws OrderNotFoundException {

		// given

		// when
		List<Order> allOrders = orderingSystem.getAllOrders();

		// then
		assertThat(allOrders.isEmpty(), is(Boolean.TRUE));

	}

	@Test
	public void shouldGetAllOrdersCreated() throws OrderNotFoundException {

		// given
		NewOrder newOrder1 = new NewOrder(5);
		NewOrder newOrder2 = new NewOrder(5);

		// when
		Order order1 = orderingSystem.createOrder(newOrder1);
		Order order2 = orderingSystem.createOrder(newOrder2);

		// when
		List<Order> allOrders = orderingSystem.getAllOrders();

		// then
		assertThat(allOrders.size(), is(2));
		assertTrue(allOrdersContains(allOrders, order1));
		assertTrue(allOrdersContains(allOrders, order2));

	}

	private boolean allOrdersContains(List<Order> allOrders, Order order) {
		return allOrders.stream().filter(o -> o.getReference().equals(order.getReference())).findFirst().isPresent();
	}

	@Test
	public void shouldUpdateExistingOrder() throws OrderNotFoundException {

		// given
		NewOrder newOrder = new NewOrder(5);
		Order order = orderingSystem.createOrder(newOrder);
		OrderUpdate orderUpdate = new OrderUpdate(10);

		// when
		Order updatedOrder = orderingSystem.updateOrder(order.getReference(), orderUpdate);

		// then
		assertThat(updatedOrder.getReference(), is(order.getReference()));
		assertThat(updatedOrder.getNumberOfBricksWanted(), is(orderUpdate.getNumberOfBricksWanted()));
		assertThat(updatedOrder.getState(), is(order.getState()));

	}

	@Test
	public void shouldDispatchExistingOrderWhenFulfilled() throws OrderCannotBeFulfiledException {

		// given
		NewOrder newOrder = new NewOrder(5);
		Order order = orderingSystem.createOrder(newOrder);

		// when
		Order updatedOrder = orderingSystem.fulfilOrder(order.getReference(), new FulfilOrder());

		// then
		assertThat(updatedOrder.getReference(), is(order.getReference()));
		assertThat(updatedOrder.getNumberOfBricksWanted(), is(order.getNumberOfBricksWanted()));
		assertThat(updatedOrder.getState(), is(State.DISPATCHED));

	}

	@Test(expected = OrderCannotBeFulfiledException.class)
	public void shouldNotDispatchOrderWhenDoesNotExist() throws OrderCannotBeFulfiledException {

		// given

		try {
			// when
			orderingSystem.fulfilOrder("invalid-reference", new FulfilOrder());
		} catch (OrderCannotBeFulfiledException e) {
			// then
			assertThat(e.getMessage(), is("Order with reference invalid-reference could not be fulfilled"));
			throw e;
		}

	}
}
