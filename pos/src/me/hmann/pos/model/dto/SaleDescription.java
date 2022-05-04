package me.hmann.pos.model.dto;

import me.hmann.pos.integration.IntegrationSystems;
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
	 * @param systems External systems needed to retrieve price.
	 * @return The total price for the sale.
	 */
	public double getTotalPrice(IntegrationSystems systems) {
		double total = 0;

		for(Map.Entry<String, Integer> entry : items.entrySet()) {
			ItemDescription itemDesc = systems.getInventorySystem().getItemDescription(entry.getKey());
			total += itemDesc.getPriceWithVAT() * entry.getValue();
		}

		return total;
	}
}
