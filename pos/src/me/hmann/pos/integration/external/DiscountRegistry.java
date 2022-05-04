package me.hmann.pos.integration.external;

import me.hmann.pos.model.Discount;

/**
 * Keeps track of discounts for different customers.
 */
public class DiscountRegistry {
	/**
	 * Get applicable discounts for a customer, if any.
	 * @param id The ID the customer has specified.
	 * @return A list of discounts (can be empty).
	 */
	public Discount[] getDiscountsForCustomer(String id) {
		return null;
	}
}
