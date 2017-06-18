package com.danscottjones.flowershop.order;

import com.danscottjones.flowershop.inventory.InventoryItem;
import com.danscottjones.flowershop.parser.InvalidInputException;

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

	public double calculateCost() {
		return bundlesBought.stream()
				.map(bundle -> bundle.getBundle().getPrice() * bundle.getQuantity())
				.reduce(0.0, Double::sum);
	}

	/**
	 * Calculate the quantities of bundles required to minimise cost.
	 */
	private void initOrderLine() throws InvalidOrderException {

		List<InventoryItem.Bundle> bundleCosts = item.getBundles();

		// reorganise bundlesBought so we have a LinkedHashMap ordered by quantity
		Map<Integer, InventoryItem.Bundle> bundleByQuantity = bundleCosts.stream()
				.sorted(Comparator.comparingInt(InventoryItem.Bundle::getQuantity).reversed())
				.collect(Collectors.toMap(
						InventoryItem.Bundle::getQuantity,
						Function.identity(),
						(oldValue, newValue) -> newValue,
						LinkedHashMap::new
				));

		int remaining = quantity;
		for (Map.Entry<Integer, InventoryItem.Bundle> entry : bundleByQuantity.entrySet()) {
			if (remaining == 0) {
				break;
			}

			int bundleSize = entry.getKey();
			if (bundleSize <= remaining) {
				// integer division will round down for us
				int bundleQuantity = remaining / bundleSize;
				bundlesBought.add(new OrderLineBundle(entry.getValue(), bundleQuantity));
				remaining -= (bundleQuantity * bundleSize);
			}
		}

		if (remaining != 0) {
			throw new InvalidOrderException("Input quantity is not a valid multiple of available " +
					"bundles");
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
