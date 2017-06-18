package com.danscottjones.flowershop.order;

import com.danscottjones.flowershop.inventory.InventoryItem;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderLineTest {

	private static final InventoryItem.Bundle TWO_BUNDLE = new InventoryItem.Bundle(2, 5);
	private static final InventoryItem.Bundle FIVE_BUNDLE = new InventoryItem.Bundle(5, 10);

	private static final InventoryItem ITEM = new InventoryItem("A01", "Item A",
			Arrays.asList(TWO_BUNDLE, FIVE_BUNDLE));

	@Test
	public void test2ItemsEquals5() throws InvalidOrderException {
		OrderLine line = new OrderLine(ITEM, 2);
		List<OrderLineBundle> orderedBundles = line.getBundlesBought();

		assertThat(orderedBundles)
				.size()
				.describedAs("Order for x2 %s contains 1 bundle", ITEM.getName())
				.isEqualTo(1);

		OrderLineBundle orderedBundle = orderedBundles.get(0);

		assertThat(orderedBundle.getBundle()).isSameAs(TWO_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 2")
				.isEqualTo(1);

		assertThat(line.calculateCost())
				.describedAs("OrderLine should cost 5")
				.isEqualTo(5.0);
	}

	@Test
	public void test5ItemsEquals10() throws InvalidOrderException {
		OrderLine line = new OrderLine(ITEM, 5);
		List<OrderLineBundle> orderedBundles = line.getBundlesBought();

		assertThat(orderedBundles)
				.size()
				.describedAs("Order for x5 %s contains 1 bundle", ITEM.getName())
				.isEqualTo(1);

		OrderLineBundle orderedBundle = orderedBundles.get(0);

		assertThat(orderedBundle.getBundle()).isSameAs(FIVE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 5")
				.isEqualTo(1);

		assertThat(line.calculateCost())
				.describedAs("OrderLine should cost 10")
				.isEqualTo(10.0);
	}

	@Test
	public void test7ItemsEquals15() throws InvalidOrderException {
		OrderLine line = new OrderLine(ITEM, 7);
		List<OrderLineBundle> orderedBundles = line.getBundlesBought();

		assertThat(orderedBundles)
				.size()
				.describedAs("Order for x7 %s contains 2 bundle lines", ITEM.getName())
				.isEqualTo(2);

		// check bundle with size 5
		OrderLineBundle orderedBundle = orderedBundles.get(0);

		assertThat(orderedBundle.getBundle()).isSameAs(FIVE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 5")
				.isEqualTo(1);

		// check bundle with size 2
		orderedBundle = orderedBundles.get(1);

		assertThat(orderedBundle.getBundle()).isSameAs(TWO_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 2")
				.isEqualTo(1);


		assertThat(line.calculateCost())
				.describedAs("OrderLine should cost 15")
				.isEqualTo(15.0);
	}

	@Test
	public void test12ItemsEquals25() throws InvalidOrderException {
		OrderLine line = new OrderLine(ITEM, 12);
		List<OrderLineBundle> orderedBundles = line.getBundlesBought();

		assertThat(orderedBundles)
				.size()
				.describedAs("Order for x12 %s contains 2 bundle lines", ITEM.getName())
				.isEqualTo(2);

		// check bundle with size 5
		OrderLineBundle orderedBundle = orderedBundles.get(0);

		assertThat(orderedBundle.getBundle()).isSameAs(FIVE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 2 bundle of size 5")
				.isEqualTo(2);

		// check bundle with size 2
		orderedBundle = orderedBundles.get(1);

		assertThat(orderedBundle.getBundle()).isSameAs(TWO_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 2")
				.isEqualTo(1);


		assertThat(line.calculateCost())
				.describedAs("OrderLine should cost 25")
				.isEqualTo(25.0);
	}

	@Test(expected = InvalidOrderException.class)
	public void testInputNotMultipleOfBundle() throws InvalidOrderException {
		OrderLine line = new OrderLine(ITEM, 3);
	}

}
