package com.danscottjones.flowershop.order;

import java.math.BigDecimal;
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

	public BigDecimal calculateTotal() {
		return this.stream()
				.map(OrderLine::calculateCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
}
