package me.hmann.pos.model.dto;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.exceptions.ItemNotFoundException;
import me.hmann.pos.model.Discount;

import java.util.List;
import java.util.Map;

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
			ItemDescription itemDesc = null;

			try {
				itemDesc = systems.getInventorySystem().getItemDescription(entry.getKey());
			} catch (ItemNotFoundException e) {
				throw new IllegalStateException(e);
			}

			total += itemDesc.getPriceWithVAT() * entry.getValue();
		}

		for(Discount discount : discounts) {
			total -= discount.getPriceReduction(systems, this);
		}

		/* The customer can't get money back from discounts */
		if(total < 0) {
			total = 0;
		}

		return total;
	}
}
