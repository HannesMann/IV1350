package me.hmann.pos.integration.internal;

import me.hmann.pos.model.dto.SaleDescription;

import java.util.ArrayList;

/***
 * Responsible for logging sales in the point-of-sale (POS) system.
 */
public class SaleLog {
	private ArrayList<SaleDescription> loggedSales = new ArrayList<>();

	/**
	 * Log a completed sale to the internal sale log.
	 * @param sale A description of the finished sale.
	 */
	public void logSale(SaleDescription sale) {
		loggedSales.add(sale);
	}
}
