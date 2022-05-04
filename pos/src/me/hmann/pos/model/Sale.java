package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;

import java.util.*;

/***
 * Responsible for storing details about a completed customer purchase.
 * Discounts may be applied, but no more items can be added.
 */
public class Sale {
	private TreeMap<String, Integer> items;
	private ArrayList<Discount> discounts;

	/**
	 * Create sale from ongoing sale.
	 * @param items Items the customer has bought.
	 */
	Sale(Map<String, Integer> items) {
		this.items = new TreeMap<String, Integer>(items);
		this.discounts = new ArrayList<Discount>();
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
		return getDescription().getTotalPrice();
	}

	/**
	 * @return A description of this sale.
	 */
	public SaleDescription getDescription() {
		return new SaleDescription(items, discounts);
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
		Receipt receipt = new Receipt(getDescription(), amountPaid);
		receipt.print(externalSystems.getPrinter());
		return receipt;
	}
}
