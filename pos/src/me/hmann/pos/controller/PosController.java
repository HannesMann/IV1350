package me.hmann.pos.controller;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.exceptions.ItemNotFoundException;
import me.hmann.pos.model.OngoingSale;
import me.hmann.pos.model.Sale;
import me.hmann.pos.model.dto.ItemDescription;

/***
 * A controller for a complete point-of-sale (POS) system.
 */
public class PosController {
	private OngoingSale ongoingSale;
	private Sale sale;

	private IntegrationSystems integrationSystems;

	/**
	 * Create new POS controller.
	 * @param integrationSystems External and internal systems used by the controller.
	 */
	public PosController(IntegrationSystems integrationSystems) {
		this.integrationSystems = integrationSystems;
	}

	/***
	 * Used by the cashier to start a new sale.
	 * If a sale is already ongoing, this function will fail.
	 */
	public void startSale() {
		if(ongoingSale != null) {
			throw new IllegalStateException("Cannot start a new sale while an ongoing sale exists.");
		}

		sale = null;
		ongoingSale = new OngoingSale();
	}

	/***
	 * Used by the cashier to complete a sale.
	 * If a sale has not started, this function will fail.
	 */
	public void endSale() {
		if(ongoingSale == null) {
			throw new IllegalStateException("No ongoing sale exists that can be ended with endSale().");
		}

		sale = ongoingSale.completeSale();
		ongoingSale = null;
	}

	/**
	 * Used by the cashier to add an item to an ongoing sale.
	 * @param itemId The internal ID of the item.
	 * @param quantity The quantity of the specified item.
	 * @return A description of the item.
	 */
	public ItemDescription recordItem(String itemId, int quantity) throws ItemNotFoundException {
		return ongoingSale.recordItem(integrationSystems, itemId, quantity);
	}

	/**
	 * @return The current running total of either the ongoing or completed sale.
	 */
	public double getCurrentRunningTotal() {
		if(ongoingSale != null) {
			return ongoingSale.getRunningTotal(integrationSystems);
		}
		else {
			return sale.getTotalPrice(integrationSystems);
		}
	}

	/**
	 * Applies any available discounts to the finished sale.
	 * @param customerId The customer making the purchase.
	 * @return The amount of discounts that were applied, this is >= 0.
	 */
	public int applyCustomerDiscounts(int customerId) {
		return sale.applyDiscounts(integrationSystems, customerId);
	}

	/**
	 * Indicate that the sale is finished and the customer has paid for their items.
	 * @param amount The amount the customer paid.
	 * @return The change to give back to the customer.
	 */
	public double payAndPrintReceipt(double amount) {
		return sale.payAndPrintReceipt(amount, integrationSystems).getChange(integrationSystems);
	}
}
