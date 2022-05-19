package me.hmann.pos.integration.exceptions;

public class ItemNotFoundException extends Exception {
	private String itemId;

	public ItemNotFoundException(String itemId) {
		super("An item with the ID \"" + itemId + "\" was not found in the store inventory.");
		this.itemId = itemId;
	}

	public String getItemId() {
		return itemId;
	}
}
