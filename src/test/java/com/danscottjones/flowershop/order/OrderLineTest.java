package com.danscottjones.flowershop.order;

import com.danscottjones.flowershop.inventory.InventoryItem;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderLineTest {

	private static final InventoryItem.Bundle THREE_BUNDLE = new InventoryItem.Bundle(3, 6);
	private static final InventoryItem.Bundle FIVE_BUNDLE = new InventoryItem.Bundle(5, 10);
	private static final InventoryItem.Bundle NINE_BUNDLE = new InventoryItem.Bundle(9, 17);

	private static final InventoryItem ITEM = new InventoryItem("A01", "Item A",
			Arrays.asList(THREE_BUNDLE, FIVE_BUNDLE));

	@Test
	public void test2ItemsEquals5() throws InvalidOrderException {
		OrderLine line = new OrderLine(ITEM, 3);
		List<OrderLineBundle> orderedBundles = line.getBundlesBought();

		assertThat(orderedBundles)
				.size()
				.describedAs("Order for x3 %s contains 1 bundle", ITEM.getName())
				.isEqualTo(1);

		OrderLineBundle orderedBundle = orderedBundles.get(0);

		assertThat(orderedBundle.getBundle()).isSameAs(THREE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of 3")
				.isEqualTo(1);

		assertThat(line.calculateCost())
				.describedAs("OrderLine should cost 6")
				.isEqualTo(6.0);
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
		OrderLine line = new OrderLine(ITEM, 8);
		List<OrderLineBundle> orderedBundles = line.getBundlesBought();

		assertThat(orderedBundles)
				.size()
				.describedAs("Order for x8 %s contains 2 bundle lines", ITEM.getName())
				.isEqualTo(2);

		// check bundle with size 5
		OrderLineBundle orderedBundle = orderedBundles.get(0);

		assertThat(orderedBundle.getBundle()).isSameAs(FIVE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 5")
				.isEqualTo(1);

		// check bundle with size 2
		orderedBundle = orderedBundles.get(1);

		assertThat(orderedBundle.getBundle()).isSameAs(THREE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 3")
				.isEqualTo(1);


		assertThat(line.calculateCost())
				.describedAs("OrderLine should cost 16")
				.isEqualTo(16.0);
	}

	@Test
	public void test13ItemsEquals25() throws InvalidOrderException {
		OrderLine line = new OrderLine(ITEM, 13);
		List<OrderLineBundle> orderedBundles = line.getBundlesBought();

		assertThat(orderedBundles)
				.size()
				.describedAs("Order for x13 %s contains 2 bundle lines", ITEM.getName())
				.isEqualTo(2);

		// check bundle with size 5
		OrderLineBundle orderedBundle = orderedBundles.get(0);

		assertThat(orderedBundle.getBundle()).isSameAs(FIVE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 2 bundle of size 5")
				.isEqualTo(2);

		// check bundle with size 2
		orderedBundle = orderedBundles.get(1);

		assertThat(orderedBundle.getBundle()).isSameAs(THREE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 3")
				.isEqualTo(1);


		assertThat(line.calculateCost())
				.describedAs("OrderLine should cost 26")
				.isEqualTo(26.0);
	}

	@Test
	public void test() {

//		solution = BundleQuantitySolver.solve(new int[] { 3, 5, 9 }, 13);
//		assertThat(solution).describedAs("3 5 9 = 13").containsExactly(5, 5, 3);

	}

	/**
	 * Test the case that the highest denomination isn't used, i.e. [ 3, 5, 9 ] = 13 should return
	 * 2 x 5 + 1 x 3.
	 *
	 * @throws InvalidOrderException
	 */
	@Test
	public void testNonGreedyMethod() throws InvalidOrderException {
		InventoryItem item = new InventoryItem("A02", "Item B",
				Arrays.asList(THREE_BUNDLE, FIVE_BUNDLE, NINE_BUNDLE));
		OrderLine line = new OrderLine(item, 13);
		List<OrderLineBundle> orderedBundles = line.getBundlesBought();

		assertThat(orderedBundles)
				.size()
				.describedAs("Order for x13 %s contains 2 bundle lines", ITEM.getName())
				.isEqualTo(2);

		// check bundle with size 5
		OrderLineBundle orderedBundle = orderedBundles.get(0);

		assertThat(orderedBundle.getBundle()).isSameAs(FIVE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 2 bundle of size 5")
				.isEqualTo(2);

		// check bundle with size 2
		orderedBundle = orderedBundles.get(1);

		assertThat(orderedBundle.getBundle()).isSameAs(THREE_BUNDLE);
		assertThat(orderedBundle.getQuantity())
				.describedAs("Expect 1 bundle of size 3")
				.isEqualTo(1);


		assertThat(line.calculateCost())
				.describedAs("OrderLine should cost 26")
				.isEqualTo(26.0);

	}

	@Test(expected = InvalidOrderException.class)
	public void testInputNotMultipleOfBundle() throws InvalidOrderException {
		OrderLine line = new OrderLine(ITEM, 4);
	}

}
