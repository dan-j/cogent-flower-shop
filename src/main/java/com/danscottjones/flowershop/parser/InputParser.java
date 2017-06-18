package com.danscottjones.flowershop.parser;

import com.danscottjones.flowershop.order.InvalidOrderException;
import com.danscottjones.flowershop.order.Order;
import com.danscottjones.flowershop.order.OrderLine;
import com.danscottjones.flowershop.inventory.Inventory;
import com.danscottjones.flowershop.inventory.InventoryItem;

import java.io.InputStream;
import java.util.Scanner;

public class InputParser {

	private Inventory inventory;

	public InputParser(Inventory inventory) {
		this.inventory = inventory;
	}

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
				throw new InvalidInputException("Item code '" + code + "' does not exist");
			}

			order.add(new OrderLine(item, quantity));

		}

		return order;
	}

}
