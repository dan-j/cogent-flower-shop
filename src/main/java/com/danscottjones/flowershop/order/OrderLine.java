package com.danscottjones.flowershop.order;

import com.danscottjones.flowershop.inventory.InventoryItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderLine {

	private InventoryItem item;
	private int quantity;

	private List<OrderLineBundle> bundlesBought = new ArrayList<>();

	public OrderLine(InventoryItem item, int quantity) throws InvalidOrderException {
		this.item = item;
		this.quantity = quantity;
		initOrderLine();
	}

	public BigDecimal calculateCost() {
		return bundlesBought.stream()
				.map(OrderLineBundle::calculateCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * Calculate the quantities of bundles required to minimise cost.
	 */
	private void initOrderLine() throws InvalidOrderException {

		List<InventoryItem.Bundle> bundleDetails = item.getBundles();

		// reorganise bundlesDetails so we have a LinkedHashMap ordered by quantity
		Map<Integer, InventoryItem.Bundle> bundleByQuantity = bundleDetails.stream()
				.sorted(Comparator.comparingInt(InventoryItem.Bundle::getQuantity).reversed())
				.collect(Collectors.toMap(
						InventoryItem.Bundle::getQuantity,
						Function.identity(),
						(oldValue, newValue) -> newValue,
						LinkedHashMap::new
				));

		int[] bundleSizes = new int[bundleDetails.size()];
		int i = 0;
		for (Integer size : bundleByQuantity.keySet()) {
			bundleSizes[i++] = size;
		}

		// here is a Map of bundles (key: bundle size, value: quantity of that bundle)
		Map<Integer, Integer> bundles = BundleQuantitySolver.solve(bundleSizes, this.quantity);

		// add the bundle quantities to the `bundlesBought` list and check that sum of bundles
		// equal required quantity
		int sum = bundles.entrySet()
				.stream()
				.peek(entry -> bundlesBought.add(new OrderLineBundle(
						bundleByQuantity.get(entry.getKey()),
						entry.getValue()))
				)
				.mapToInt(entry -> entry.getKey() * entry.getValue())
				.sum();

		if (sum != this.quantity) {
			throw new InvalidOrderException(
					"Input quantity is not a valid multiple of available bundles");
		}
	}

	public InventoryItem getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}

	public List<OrderLineBundle> getBundlesBought() {
		return bundlesBought;
	}
}
