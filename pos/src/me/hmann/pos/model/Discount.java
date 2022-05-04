package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.model.dto.SaleDescription;

/***
 * Defines rules for a discount that can be applied to a sale.
 */
public interface Discount {
	/***
	 * @param systems External integration systems for looking up name, price, etc.
	 * @return A description of this discount such as "Buy 3 tomato soup, pay for 2".
	 */
	public String getDescription(IntegrationSystems systems);

	/***
	 * Checks if a discount can be applied to a sale.
	 * @param systems External integration systems for looking up name, price, etc.
	 * @param sale The description of a purchase made by the customer.
	 * @return If this discount will apply any price reduction to the sale.
	 */
	public boolean doesDiscountApply(IntegrationSystems systems, SaleDescription sale);

	/***
	 * Applies this discount to a sale.
	 * @param systems External integration systems for looking up name, price, etc.
	 * @param sale The description of a purchase made by the customer.
	 * @return The amount of price reduction for the sale (this includes VAT), this must be >= 0.
	 */
	public double getPriceReduction(IntegrationSystems systems, SaleDescription sale);
}
