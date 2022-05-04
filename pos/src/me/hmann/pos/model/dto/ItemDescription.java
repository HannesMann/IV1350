package me.hmann.pos.model.dto;

/***
 * Defines properties of an item.
 */
public class ItemDescription {
	private String id;
	private String name;

	private double price;
	private double taxRate;

	public ItemDescription(String id, String name, double price, double taxRate) {
		this.id = id;
		this.name = name;

		this.price = price;
		this.taxRate = taxRate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public double getTaxRate() {
		return taxRate;
	}
}
