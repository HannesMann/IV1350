package me.hmann.pos.integration.external;

import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;
import me.hmann.pos.model.dto.TaxRate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Keeps track of items in the store, their price, tax rate, etc.
 */
public class InventorySystem {
	private ArrayList<SaleDescription> loggedSales = new ArrayList<>();

	/**
	 * Used in the seminar for some example items. This would be fetched from a database in a real application.
	 */
	public final static HashMap<String, ItemDescription> AVAILABLE_ITEMS = new HashMap<>();

	static {
		AVAILABLE_ITEMS.put("SAUSG", new ItemDescription("Sausage 400 g", 29.99, TaxRate.RATE_25));
		AVAILABLE_ITEMS.put("MEATB", new ItemDescription("Meatballs 1 kg", 49.99, TaxRate.RATE_25));

		AVAILABLE_ITEMS.put("POTAT", new ItemDescription("Potato 1 kg", 9.99, TaxRate.RATE_12));
		AVAILABLE_ITEMS.put("BROCO", new ItemDescription( "Broccoli 1 kg", 42.99, TaxRate.RATE_6));

		AVAILABLE_ITEMS.put("BREAD", new ItemDescription("Bread 6 pack", 20.00, TaxRate.RATE_6));
		AVAILABLE_ITEMS.put("TOAST", new ItemDescription("Toast 10 pack", 15.00, TaxRate.RATE_6));
		AVAILABLE_ITEMS.put("TORTL", new ItemDescription("Tortilla 8 pack", 25.00, TaxRate.RATE_25));

		AVAILABLE_ITEMS.put("COLA3", new ItemDescription("Coca-Cola 33 cl", 9.50, TaxRate.RATE_25));
		AVAILABLE_ITEMS.put("COLA5", new ItemDescription("Coca-Cola 50 cl", 15.00, TaxRate.RATE_25));
		AVAILABLE_ITEMS.put("COLA1", new ItemDescription("Coca-Cola 1 l", 20.00, TaxRate.RATE_25));
	}

	/**
	 * Get a full description of an item based on its identifier.
	 * @param id The internal ID of the item.
	 * @return The item description, or null.
	 */
	public ItemDescription getItemDescription(String id) {
		return AVAILABLE_ITEMS.getOrDefault(id, null);
	}

	/**
	 * Indicate that a purchase has been made by the customer. This will update available inventory statistics.
	 * @param sale A description of the purchase made by the customer.
	 */
	public void logSale(SaleDescription sale) {
		loggedSales.add(sale);
	}
}
