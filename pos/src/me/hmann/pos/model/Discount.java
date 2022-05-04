package me.hmann.pos.model;

import me.hmann.pos.model.dto.SaleDescription;

/***
 * Defines rules for a discount that can be applied to a sale.
 */
public class Discount {
	/***
	 * @return A description of this sale, such as "Buy 3 tomato soup, pay for 2".
	 */
	public String getDescription() {
		return "";
	}

	/***
	 * Applies this discount to a sale.
	 * @param sale The description of a purchase made by the customer.
	 * @return The amount of price reduction for the sale, this must be >= 0.
	 */
	public double getPriceReduction(SaleDescription sale) {
		return 0;
	}
}
