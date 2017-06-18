package com.danscottjones.flowershop.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Loader class for reading JSON input and converting it to an instance of {@link Inventory}.
 * <p>
 * By default the loader will use the config provided by this module in {@code
 * src/main/resources/default-config.json}, otherwise the user can specify a path to a JSON file,
 * or an instance of {@link Resource}.
 */
public class InventoryLoader {

	private final Resource inventoryConfig;
	private final ObjectMapper objectMapper;

	public InventoryLoader() {
		this(new ClassPathResource("/default-config.json"), new ObjectMapper());
	}

	public InventoryLoader(String inventoryConfigPath) {
		this(new FileSystemResource(inventoryConfigPath), new ObjectMapper());
	}

	public InventoryLoader(Resource inventoryConfig, ObjectMapper objectMapper) {
		this.inventoryConfig = inventoryConfig;
		this.objectMapper = objectMapper;
	}

	/**
	 * Uses Jackson's {@link ObjectMapper} to convert JSON into an instance of {@link Inventory}
	 *
	 * @return an instance of {@link Inventory} populated with details from the parsed JSON file
	 * @throws InventoryLoaderException if the JSON is unable to be parsed
	 */
	public Inventory load() throws InventoryLoaderException {
		try (InputStream is = this.inventoryConfig.getInputStream()) {
			return new Inventory(
					this.objectMapper.readValue(
							is,
							new TypeReference<List<InventoryItem>>() { }
					)
			);
		} catch (IOException e) {
			throw new InventoryLoaderException("Unable to parse configuration file", e);
		}

	}
}
