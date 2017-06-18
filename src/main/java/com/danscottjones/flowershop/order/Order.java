package com.danscottjones.flowershop.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represent an order of flowers, as parsed from the input of one execution of the application, i.e.
 * if multiple lines are inputted when executing the program, these are treated as a single order.
 */
public class Order {

	/**
	 * Each line of input is parsed into an OrderLine
	 */
	private List<OrderLine> orderLines;

	public Order() {
		this.orderLines = new ArrayList<>();
	}

	public boolean add(OrderLine orderLine) {
		return orderLines.add(orderLine);
	}

	public Stream<OrderLine> stream() {
		return orderLines.stream();
	}

	/**
	 * Iterate over the order lines and calculate the total cost.
	 * @return
	 */
	public BigDecimal calculateTotal() {
		return this.stream()
				.map(OrderLine::calculateCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
}
