package com.danscottjones.flowershop;

import com.danscottjones.flowershop.inventory.Inventory;
import com.danscottjones.flowershop.inventory.InventoryItem;
import com.danscottjones.flowershop.inventory.InventoryLoader;
import com.danscottjones.flowershop.order.Order;
import com.danscottjones.flowershop.order.OrderLine;
import com.danscottjones.flowershop.order.OrderLineBundle;
import com.danscottjones.flowershop.parser.InputParser;

import java.io.PrintWriter;

public class Application {

	public static void main(String[] args) throws Exception {
		Inventory inventory = new InventoryLoader().load();
		InputParser parser = new InputParser(inventory);
		Order order = parser.parseStream(System.in);

		PrintWriter writer = new PrintWriter(System.out);


		for (OrderLine line : order.getOrderLines()) {
			writer.println(line.getQuantity() + " " + line.getItem().getCode() + " $" + line
					.calculateCost());

			for (OrderLineBundle lineBundle : line.getBundlesBought()) {
				InventoryItem.Bundle bundle = lineBundle.getBundle();
				writer.println("\t" + lineBundle.getQuantity() + " x "
						+ bundle.getQuantity() + " $" + bundle.getPrice());
			}
		}

		writer.println("Total cost:\t$" + order.calculateTotal());

		writer.flush();
	}
}
