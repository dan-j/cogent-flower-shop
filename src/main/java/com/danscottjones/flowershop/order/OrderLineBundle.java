package com.danscottjones.flowershop.order;

import com.danscottjones.flowershop.inventory.InventoryItem;

public class OrderLineBundle {

	private InventoryItem.Bundle bundle;
	private int quantity;

	public OrderLineBundle(InventoryItem.Bundle bundle, int quantity) {
		this.bundle = bundle;
		this.quantity = quantity;
	}

	public InventoryItem.Bundle getBundle() {
		return bundle;
	}

	public int getQuantity() {
		return quantity;
	}

}
