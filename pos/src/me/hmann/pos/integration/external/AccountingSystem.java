package me.hmann.pos.integration.external;

import me.hmann.pos.model.dto.SaleDescription;

import java.util.ArrayList;

/**
 * External accounting system.
 */
public class AccountingSystem {
	private ArrayList<SaleDescription> loggedSales = new ArrayList<>();

	/**
	 * Log a completed sale to the accounting system.
	 * @param sale A description of the purchase made by the customer.
	 */
	public void logSale(SaleDescription sale) {
		loggedSales.add(sale);
	}
}
