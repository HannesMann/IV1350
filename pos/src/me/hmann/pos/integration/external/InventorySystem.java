package me.hmann.pos.integration.external;

import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;

/**
 * Keeps track of items in the store, their price, tax rate, etc.
 */
public class InventorySystem {
	/**
	 * Get a full description of an item based on its identifier.
	 * @param id The internal ID of the item.
	 * @return The item description, or null.
	 */
	public ItemDescription getItemDescription(String id) {
		return null;
	}

	/**
	 * Indicate that a purchase has been made by the customer. This will update available inventory statistics.
	 * @param sale A description of the purchase made by the customer.
	 */
	public void logSale(SaleDescription sale) {

	}
}
