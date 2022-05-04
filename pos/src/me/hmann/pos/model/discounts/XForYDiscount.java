package me.hmann.pos.model.discounts;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.model.Discount;
import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;

/**
 * This discount type will reduce the price if a specific number of items are bought.
 */
public class XForYDiscount implements Discount {
	private String itemId;
	private int itemsToBuy;
	private int itemsToPayFor;

	/**
	 * Create a new discount for a specific item, where the customer can buy X amount and only pay for Y.
	 * @param itemId The item this discount will apply to.
	 * @param itemsToBuy The number of items the customer gets.
	 * @param itemsToPayFor The number of items the customer pays for.
	 */
	public XForYDiscount(String itemId, int itemsToBuy, int itemsToPayFor) {
		this.itemId = itemId;
		this.itemsToBuy = itemsToBuy;
		this.itemsToPayFor = itemsToPayFor;
	}

	@Override
	public String getDescription(IntegrationSystems systems) {
		ItemDescription itemDesc = systems.getInventorySystem().getItemDescription(itemId);
		return "Buy " + itemsToBuy + " " + itemDesc.getName() + ", Pay For " + itemsToPayFor;
	}

	@Override
	public boolean doesDiscountApply(IntegrationSystems systems, SaleDescription sale) {
		return getPriceReduction(systems, sale) > 0;
	}

	@Override
	public double getPriceReduction(IntegrationSystems systems, SaleDescription sale) {
		double priceReduction = 0;

		if(sale.getItems().containsKey(itemId)) {
			ItemDescription itemDesc = systems.getInventorySystem().getItemDescription(itemId);
			double reductionAmount = itemsToBuy * itemDesc.getPriceWithVAT() - itemsToPayFor * itemDesc.getPriceWithVAT();

			int itemsLeft = sale.getItems().get(itemId);
			/* Apply price reduction recursively. */
			while(itemsLeft >= itemsToBuy) {
				priceReduction += reductionAmount;
				itemsLeft -= itemsToBuy;
			}
		}

		return priceReduction;
	}
}
