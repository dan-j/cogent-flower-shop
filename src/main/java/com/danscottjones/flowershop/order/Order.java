package com.danscottjones.flowershop.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Order {

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

	public double calculateTotal() {
		return this.stream()
				.map(OrderLine::calculateCost)
				.reduce(0.0, Double::sum);
	}
}
