package me.hmann.pos.model;

import java.util.List;

/**
 * Receives notifications during a sale from start to finish.
 */
public interface SaleObserver {
	/**
	 * Called when an item has been added.
	 * @param itemId The item identifier.
	 * @param newQuantity The amount of the specified item now added to the sale.
	 */
	void onItemQuantityIncreased(String itemId, int newQuantity);

	/**
	 * Called when one or more discounts were successfully applied to the sale.
	 * @param discounts The list of discounts applied.
	 */
	void onDiscountsApplied(List<Discount> discounts);

	/**
	 * Called when a customer has paid for the sale.
	 * @param revenue Customer payment excluding taxes and change (how much money the store receives).
	 * @param payment Total customer payment.
	 */
	void onCustomerPayment(double revenue, double payment);
}
