package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * Responsible for storing details about a completed customer purchase.
 * Discounts may be applied, but no more items can be added.
 */
public class Sale {
	private SaleDescription saleDescription;

	Sale(Map<String, Integer> items) {

	}

	/***
	 * Fetches discounts from the database and applies them to the purchase.
	 * @param customerId The customer identifier as entered by the cashier.
	 * @return The amount of discounts that were applied, this is >= 0.
	 */
	public int applyDiscounts(String customerId) {
		return 0;
	}

	/***
	 * @return The current total price of the sale, including VAT (taxes) and discounts.
	 */
	public double getTotalPrice() {
		return saleDescription.getTotalPrice();
	}

	/***
	 * Allows a customer to pay for the sale and get a receipt.
	 *
	 * If the amount is larger than total price, the receipt will show the change.
	 * If the amount is smaller than total price, this function will throw an error.
	 *
	 * The sale will be logged when this method is called.
	 * @param amountPaid The amount the customer paid.
	 * @param externalSystems External systems that are used to log the sale.
	 * @return A receipt for the sale.
	 */
	public Receipt payAndPrintReceipt(double amountPaid, IntegrationSystems externalSystems) {
		Receipt receipt = new Receipt(saleDescription, amountPaid);
		receipt.print(externalSystems.getPrinter());
		return receipt;
	}
}
