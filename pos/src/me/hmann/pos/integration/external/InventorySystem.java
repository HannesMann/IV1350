package me.hmann.pos.integration.external;

import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;
import me.hmann.pos.model.dto.TaxRate;
import sun.security.x509.AVA;

import java.util.TreeMap;

/**
 * Keeps track of items in the store, their price, tax rate, etc.
 */
public class InventorySystem {
	/**
	 * Used in the seminar for some example items. This would be fetched from a database in a real application.
	 */
	public final static TreeMap<String, ItemDescription> AVAILABLE_ITEMS = new TreeMap<String, ItemDescription>();

	static {
		AVAILABLE_ITEMS.put("PKORV", new ItemDescription("PKORV", "Prinskorv 400 g", 29.99, TaxRate.RATE_25));
		AVAILABLE_ITEMS.put("KOTTB", new ItemDescription("KOTTB", "Köttbullar 1 kg", 49.99, TaxRate.RATE_25));

		AVAILABLE_ITEMS.put("POTAT", new ItemDescription("POTAT", "Potatis 1 kg", 9.99, TaxRate.RATE_12));
		AVAILABLE_ITEMS.put("BROCO", new ItemDescription("BROCO", "Broccoli 1 kg", 42.99, TaxRate.RATE_6));

		AVAILABLE_ITEMS.put("LBROD", new ItemDescription("LBROD", "Lantbröd", 20.00, TaxRate.RATE_6));
		AVAILABLE_ITEMS.put("RBROD", new ItemDescription("RBROD", "Rostbröd", 15.00, TaxRate.RATE_6));
		AVAILABLE_ITEMS.put("TORTL", new ItemDescription("TORTL", "Tortilla 8 pack", 25.00, TaxRate.RATE_25));

		AVAILABLE_ITEMS.put("COLA3", new ItemDescription("COLA3", "Coca-Cola 33 cl", 9.50, TaxRate.RATE_25));
		AVAILABLE_ITEMS.put("COLA5", new ItemDescription("COLA5", "Coca-Cola 50 cl", 15.00, TaxRate.RATE_25));
		AVAILABLE_ITEMS.put("COLA1", new ItemDescription("COLA1", "Coca-Cola 1 l", 20.00, TaxRate.RATE_25));
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

	}
}
