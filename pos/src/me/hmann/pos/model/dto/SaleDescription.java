package me.hmann.pos.model.dto;

import me.hmann.pos.model.Discount;

import java.util.*;

/**
 * Describes a sale to the customer.
 */
public class SaleDescription {
	private Map<String, Integer> items;
	private List<Discount> discounts;

	/**
	 * Create new sale description.
	 * @param items The items the customer has bought.
	 * @param discounts The discounts applied to the sale.
	 */
	public SaleDescription(Map<String, Integer> items, List<Discount> discounts) {
		this.items = items;
		this.discounts = discounts;
	}

	/**
	 * @return The items the customer has bought.
	 */
	public Map<String, Integer> getItems() {
		return items;
	}
	/**
	 * @return The discounts applied to the sale.
	 */
	public List<Discount> getAppliedDiscounts() {
		return discounts;
	}

	/**
	 * @return The total price for the sale.
	 */
	public double getTotalPrice() {
		return 0;
	}
}
