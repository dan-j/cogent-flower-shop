package com.danscottjones.flowershop.parser;

import com.danscottjones.flowershop.inventory.Inventory;
import com.danscottjones.flowershop.inventory.InventoryItem;
import com.danscottjones.flowershop.order.InvalidOrderException;
import com.danscottjones.flowershop.order.Order;
import com.danscottjones.flowershop.order.OrderLine;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Parser which reads orders inputted through an {@link InputStream}. It uses a fairly limited
 * scanner approach whereby we expect a NUMBER-STRING input, denoting quantity and item code
 * respectively. If we receive input not in this format, or an item code isn't recognised, we
 * throw an Exception.
 */
public class InputParser {

	private Inventory inventory;

	public InputParser(Inventory inventory) {
		this.inventory = inventory;
	}

	/**
	 * Parse an {@link InputStream} containing order information. This will block until the input
	 * is closed.
	 *
	 * @param input The input to parse
	 * @return a parsed {@link Order}
	 * @throws InvalidInputException if input doesn't match the required format or if the
	 *                               {@link Scanner} has any other issues reading the input
	 * @throws InvalidOrderException if an inputted item code doesn't exist or the quantity isn't
	 *                               achievable with the available bundle sizes
	 */
	public Order parseStream(InputStream input)
			throws InvalidInputException, InvalidOrderException {
		Scanner scanner = new Scanner(input);

		Order order = new Order();

		while (scanner.hasNext()) {
			int quantity = scanner.nextInt();

			if (!scanner.hasNext()) {
				throw new InvalidInputException("Line is missing item code");
			}

			String code = scanner.next();

			InventoryItem item = this.inventory.getByCode(code);

			if (item == null) {
				throw new InvalidOrderException("Item code '" + code + "' does not exist");
			}

			order.add(new OrderLine(item, quantity));

		}

		return order;
	}

}
