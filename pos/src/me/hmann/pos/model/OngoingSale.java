package me.hmann.pos.model;

import me.hmann.pos.model.dto.ItemDescription;

import java.util.HashMap;

/***
 * Responsible for storing details about an ongoing purchase.
 * Once the sale is complete, a purchase can be made by calling @{link completeSale}.
 */
public class OngoingSale {
	private HashMap<String, Integer> items;

	/***
	 * Records one or more of the same item with the specified id in the sale.
	 * @param itemId The item identifier.
	 * @param quantity The quantity entered by the cashier. This must be >= 1.
	 * @return The item description from the external inventory system, or null if no such item exists.
	 */
	public ItemDescription recordItem(String itemId, int quantity) {
		return null;
	}

	/***
	 * @return The current running total of the sale, including VAT (taxes).
	 */
	public double getRunningTotal() {
		return 0;
	}

	/***
	 * Completes this sale. At this point no more items can be added.
	 * @return A new sale object containing all items in this sale.
	 */
	public Sale completeSale() {
		return new Sale(items);
	}
}
