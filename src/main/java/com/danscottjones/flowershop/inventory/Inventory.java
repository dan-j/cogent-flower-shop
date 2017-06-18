package com.danscottjones.flowershop.inventory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represent an inventory of the Flower Shop, an inventory has many {@link InventoryItem}s which can
 * be retrieved together, or looked up by the item's {@code code}.
 */
public class Inventory {

	private final List<InventoryItem> inventoryItems;
	private final Map<String, InventoryItem> itemsByCode;

	public Inventory(List<InventoryItem> inventoryItems) {
		this.inventoryItems = inventoryItems;

		// convert the list into a map with the item's `code` as the key. Useful for direct lookups
		this.itemsByCode = this.inventoryItems.stream().collect(Collectors.toMap(
				InventoryItem::getCode, Function.identity()
		));
	}

	public InventoryItem getByCode(String code) {
		return this.itemsByCode.get(code);
	}

	public List<InventoryItem> getInventoryItems() {
		return inventoryItems;
	}

}
