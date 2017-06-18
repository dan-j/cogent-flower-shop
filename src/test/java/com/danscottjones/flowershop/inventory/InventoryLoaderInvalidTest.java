package com.danscottjones.flowershop.inventory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.isA;

@RunWith(Parameterized.class)
public class InventoryLoaderInvalidTest {

	private String resourcePath;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	public InventoryLoaderInvalidTest(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	@Test
	public void testInvalidInputThrows() throws Exception {
		thrown.expect(InventoryLoaderException.class);
		thrown.expectCause(isA(JsonMappingException.class));

		InventoryLoader loader = new InventoryLoader(
				new ClassPathResource(this.resourcePath),
				new ObjectMapper());

		// should throw
		loader.load();
	}

	@Parameters(name = "{index} testInvalidInputThrows(\"{0}\")")
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[] { "/test-missing-code.json" },
				new Object[] { "/test-missing-name.json" },
				new Object[] { "/test-missing-price.json" },
				new Object[] { "/test-missing-quantity.json" }
		);
	}
}
