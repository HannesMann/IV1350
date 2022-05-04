package me.hmann.pos.model.dto;

/***
 * Defines properties of an item.
 */
public class ItemDescription {
	private String id;
	private String name;

	private double price;
	private double taxRate;

	/**
	 * Create a new item description.
	 * @param id The internal ID of the item.
	 * @param name The name of the item.
	 * @param price The price of the item.
	 * @param taxRate The tax rate/VAT applied to the item at checkout.
	 */
	public ItemDescription(String id, String name, double price, double taxRate) {
		this.id = id;
		this.name = name;

		this.price = price;
		this.taxRate = taxRate;
	}

	/**
	 * @return The internal ID of the item.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The name of the item.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The price of the item.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return The price of the item with VAT applied.
	 */
	public double getPriceWithVAT() {
		return price + (price * taxRate);
	}

	/**
	 * @return The tax rate/VAT that should be applied at checkout to this item.
	 */
	public double getTaxRate() {
		return taxRate;
	}
}
