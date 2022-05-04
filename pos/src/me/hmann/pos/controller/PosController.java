package me.hmann.pos.controller;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.model.OngoingSale;
import me.hmann.pos.model.Receipt;
import me.hmann.pos.model.Sale;
import me.hmann.pos.model.dto.ItemDescription;

/***
 * A controller for a complete point-of-sale (POS) system.
 */
public class PosController {
	private OngoingSale ongoingSale;
	private Sale sale;

	private IntegrationSystems integrationSystems;

	public PosController(IntegrationSystems integrationSystems) {
		this.integrationSystems = integrationSystems;
	}

	/***
	 * Used by the cashier to start a new sale.
	 * If a sale is already ongoing, this function will fail.
	 */
	public void startSale() {
		/* TODO: Throw exception if sale is ongoing! */
		sale = null;
		ongoingSale = new OngoingSale();
	}

	/***
	 * Used by the cashier to complete a sale.
	 * If a sale has not started, this function will fail.
	 */
	public void endSale() {
		/* TODO: Throw exception if sale is not ongoing! */
		sale = ongoingSale.completeSale();
		ongoingSale = null;
	}

	public ItemDescription recordItem(String itemId, int quantity) {
		return ongoingSale.recordItem(itemId, quantity);
	}

	public double getCurrentRunningTotal() {
		if(ongoingSale != null) {
			return ongoingSale.getRunningTotal();
		}
		else {
			return sale.getTotalPrice();
		}
	}

	public void applyCustomerDiscounts(String customerId) {

	}

	public double payAndPrintReceipt(double amount) {
		return sale.payAndPrintReceipt(amount, integrationSystems).getChange();
	}
}
