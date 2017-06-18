package com.danscottjones.flowershop.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryLoaderTest {

	@Test
	public void testParserCanParse() throws InventoryLoaderException {
		Inventory inventory = loadTestSingle();

		InventoryItem item = inventory.getInventoryItems().get(0);
		assertRosesItem(item);
	}

	@Test
	public void testGetByCode() throws InventoryLoaderException {
		Inventory inventory = loadTestSingle();

		InventoryItem item = inventory.getByCode("R12");
		assertRosesItem(item);
	}

	@Test
	public void testGetByCodeMissingIsNullr() throws InventoryLoaderException {
		Inventory inventory = loadTestSingle();

		// L09 isn't in test-single.json
		InventoryItem item = inventory.getByCode("L09");
		assertThat(item).isNull();
	}

	private Inventory loadTestSingle() throws InventoryLoaderException {
		InventoryLoader loader = new InventoryLoader(
				new ClassPathResource("/test-single.json"),
				new ObjectMapper());
		Inventory inventory = loader.load();

		assertThat(inventory).isNotNull();
		assertThat(inventory.getInventoryItems()).size().isEqualTo(1);
		return inventory;
	}

	private void assertRosesItem(InventoryItem item) {
		assertThat(item.getName()).isEqualTo("Roses");
		assertThat(item.getCode()).isEqualTo("R12");

		assertThat(item.getBundles()).size().isEqualTo(1);
		InventoryItem.Bundle bundle = item.getBundles().get(0);

		assertThat(bundle.getQuantity()).isEqualTo(5);
		assertThat(bundle.getPrice()).isEqualTo(BigDecimal.valueOf(6.99));
	}

}
