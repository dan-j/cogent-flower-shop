package com.danscottjones.flowershop.order;

import com.danscottjones.flowershop.inventory.InventoryItem;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

	private static final InventoryItem.Bundle TWO_BUNDLE = new InventoryItem.Bundle(2, 5);
	private static final InventoryItem.Bundle FIVE_BUNDLE = new InventoryItem.Bundle(5, 10);
	private static final InventoryItem.Bundle THREE_BUNDLE = new InventoryItem.Bundle(3, 7);

	private static InventoryItem ITEM_A = new InventoryItem("A01", "Item A",
			Arrays.asList(TWO_BUNDLE, FIVE_BUNDLE));

	private static final InventoryItem ITEM_B = new InventoryItem("A01", "Item A",
			Arrays.asList(THREE_BUNDLE));

	@Test
	public void testMultipleOrderLines() throws InvalidOrderException {
		Order order = new Order();
		order.add(new OrderLine(ITEM_A, 12));
		order.add(new OrderLine(ITEM_B, 15));

		// orderline 1 = 2x 5-bundle + 1x 2-bundle = 25
		// orderline 2 = 5x 3-bundle = 35
		// total = 60

		assertThat(order.calculateTotal())
				.describedAs("Total order should cost 60")
				.isEqualTo(60.0);
	}
}
