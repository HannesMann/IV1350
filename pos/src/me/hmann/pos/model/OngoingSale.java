package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.exceptions.ItemNotFoundException;
import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;

import java.util.ArrayList;
import java.util.HashMap;

/***
 * Responsible for storing details about an ongoing purchase.
 * Once the sale is complete, a purchase can be made by calling @{link completeSale}.
 */
public class OngoingSale {
	private HashMap<String, Integer> items;
	private ArrayList<SaleObserver> observers;

	/**
	 * Start a new sale.
	 */
	public OngoingSale() {
		items = new HashMap<>();
		observers = new ArrayList<>();
	}

	/**
	 * Registers a new observer that will be notified when various actions happen during the sale.
	 * The observer will be carried over once completeSale is called.
	 * @param observer The observer.
	 */
	public void addSaleObserver(SaleObserver observer) {
		observers.add(observer);
	}

	/***
	 * Records one or more of the same item with the specified id in the sale.
	 * If an item with the specified ID doesn't exist an exception of type ItemNotFoundException is thrown.
	 * @param systems External systems needed to record item.
	 * @param itemId The item identifier.
	 * @param quantity The quantity entered by the cashier. This must be >= 1.
	 * @return The item description from the external inventory system.
	 */
	public ItemDescription recordItem(IntegrationSystems systems, String itemId, int quantity) throws ItemNotFoundException {
		ItemDescription itemDesc = systems.getInventorySystem().getItemDescription(itemId);

		if(items.containsKey(itemId)) {
			items.replace(itemId, items.get(itemId) + quantity);
		}
		else {
			items.put(itemId, quantity);
		}

		for(SaleObserver observer : observers) {
			observer.onItemQuantityIncreased(itemId, items.get(itemId));
		}

		return itemDesc;
	}

	/***
	 * @param systems External systems needed to retrieve price.
	 * @return The current running total of the sale, including VAT (taxes).
	 */
	public double getRunningTotal(IntegrationSystems systems) {
		return new SaleDescription(items, new ArrayList<>()).getTotalPrice(systems);
	}

	/***
	 * Completes this sale. At this point no more items can be added.
	 * @return A new sale object containing all items in this sale.
	 */
	public Sale completeSale() {
		return new Sale(items, observers);
	}
}
