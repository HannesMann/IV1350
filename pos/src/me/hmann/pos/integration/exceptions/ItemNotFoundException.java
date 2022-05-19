package me.hmann.pos.integration.exceptions;

import me.hmann.pos.integration.external.InventorySystem;

/**
 * Indicates that a search was made for an item ID that doesn't exist in the inventory system.
 */
public class ItemNotFoundException extends Exception {
	private InventorySystem inventory;
	private String itemId;

	/**
	 * Create a new exception, with information of what inventory the error originated from and what item was searched for.
	 * @param inventory The inventory system the search was performed in.
	 * @param itemId The item ID that was not found in the inventory system.
	 */
	public ItemNotFoundException(InventorySystem inventory, String itemId) {
		super("An item with the ID \"" + itemId + "\" was not found in the store inventory.");

		this.inventory = inventory;
		this.itemId = itemId;
	}

	/**
	 * @return The inventory system that was used to search for the item ID.
	 */
	public InventorySystem getInventory() {
		return inventory;
	}

	/**
	 * @return The item ID that was not found in the inventory system.
	 */
	public String getItemId() {
		return itemId;
	}
}
