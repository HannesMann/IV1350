package me.hmann.pos.model.discounts;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.model.Discount;
import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;

/**
 * This discount type applies a simple percentage reduction on an item.
 */
public class PercentageDiscount implements Discount {
	private String itemId;
	private double discountAmount;

	/**
	 * Create a new discount for a specific item, where the customer gets a percentage reduction in price on that item.
	 * @param itemId The item this discount will apply to.
	 * @param discountAmount The percentage the item will be reduced in price, from 0.01 (1%) to 1.0 (100%).
	 */
	public PercentageDiscount(String itemId, double discountAmount) {
		this.itemId = itemId;
		this.discountAmount = discountAmount;
	}

	@Override
	public String getDescription(IntegrationSystems systems) {
		ItemDescription itemDesc = systems.getInventorySystem().getItemDescription(itemId);
		return "-" + Math.round(discountAmount * 100) + "% " + itemDesc.getName();
	}

	@Override
	public boolean doesDiscountApply(IntegrationSystems systems, SaleDescription sale) {
		return sale.getItems().containsKey(itemId);
	}

	@Override
	public double getPriceReduction(IntegrationSystems systems, SaleDescription sale) {
		double priceReduction = 0;

		if(sale.getItems().containsKey(itemId)) {
			ItemDescription itemDesc = systems.getInventorySystem().getItemDescription(itemId);
			double reductionAmount = itemDesc.getPriceWithVAT() * discountAmount;

			int itemsLeft = sale.getItems().get(itemId);
			/* Apply price reduction recursively. */
			while(itemsLeft > 0) {
				priceReduction += reductionAmount;
				itemsLeft--;
			}
		}

		return priceReduction;
	}
}
