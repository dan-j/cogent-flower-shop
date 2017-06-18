package com.danscottjones.flowershop.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * POJO representing an item in the inventory. An item has a code and a name, and 0-many
 * {@link Bundle}.
 */
public class InventoryItem {

	private String code;
	private String name;
	private List<Bundle> bundles;

	@JsonCreator
	public InventoryItem(@JsonProperty(value = "code", required = true) String code,
	                     @JsonProperty(value = "name", required = true) String name,
	                     @JsonProperty(value = "bundles", required = true) List<Bundle> bundles) {
		this.code = code;
		this.name = name;
		this.bundles = bundles;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Bundle> getBundles() {
		return bundles;
	}

	public void setBundles(List<Bundle> bundles) {
		this.bundles = bundles;
	}

	/**
	 * Information for a bundle of flowers. This class will tell you how much a bundle of X
	 * flowers costs.
	 */
	public static class Bundle {
		private int quantity;
		private BigDecimal price;

		@JsonCreator
		public Bundle(@JsonProperty(value = "quantity", required = true) int quantity,
		              @JsonProperty(value = "price", required = true) BigDecimal price) {
			this.quantity = quantity;
			this.price = price;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}
	}
}
