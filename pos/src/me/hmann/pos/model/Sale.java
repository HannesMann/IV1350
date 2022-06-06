package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.exceptions.ItemNotFoundException;
import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/***
 * Responsible for storing details about a completed customer purchase.
 * Discounts may be applied, but no more items can be added.
 */
public class Sale {
	private TreeMap<String, Integer> items;
	private ArrayList<Discount> discounts;

	private ArrayList<SaleObserver> observers;

	/**
	 * Create sale from ongoing sale.
	 * @param items Items the customer has bought.
	 */
	Sale(Map<String, Integer> items, ArrayList<SaleObserver> observers) {
		this.items = new TreeMap<>(items);
		this.discounts = new ArrayList<>();
		this.observers = observers;
	}

	/**
	 * Registers a new observer that will be notified when various actions happen during the sale.
	 * @param observer The observer.
	 */
	public void addSaleObserver(SaleObserver observer) {
		observers.add(observer);
	}

	/***
	 * Fetches discounts from the database and applies them to the purchase.
	 * @param systems External systems needed to fetch discounts.
	 * @param customerId The customer identifier as entered by the cashier.
	 * @return The amount of discounts that were applied, this is >= 0.
	 */
	public int applyDiscounts(IntegrationSystems systems, int customerId) {
		SaleDescription descriptionOfCurrentSale = getDescription();
		Discount[] potentialDiscounts = systems.getDiscountRegistry().getDiscountsForCustomer(customerId);

		ArrayList<Discount> applied = new ArrayList<>();
		for(Discount discount : potentialDiscounts) {
			if(discount.doesDiscountApply(systems, descriptionOfCurrentSale)) {
				applied.add(discount);
			}
		}

		discounts.addAll(applied);

		for(SaleObserver observer : observers) {
			observer.onDiscountsApplied(applied);
		}

		return applied.size();
	}

	/***
	 * @param systems External systems needed to retrieve price.
	 * @return The current total price of the sale, including VAT (taxes) and discounts.
	 */
	public double getTotalPrice(IntegrationSystems systems) {
		return getDescription().getTotalPrice(systems);
	}

	/**
	 * @return A description of this sale.
	 */
	private SaleDescription getDescription() {
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
	 * @throws IllegalStateException If this sale has been created with invalid data.
	 * @return A receipt for the sale.
	 */
	public Receipt payAndPrintReceipt(double amountPaid, IntegrationSystems externalSystems) {
		SaleDescription saleDesc = getDescription();

		externalSystems.getSaleLog().logSale(saleDesc);
		externalSystems.getAccountingSystem().logSale(saleDesc);
		externalSystems.getInventorySystem().logSale(saleDesc);

		externalSystems.getCashRegister().depositCash(amountPaid);

		Receipt receipt = new Receipt(getDescription(), amountPaid);
		receipt.print(externalSystems);

		double storeRevenue = saleDesc.getTotalPrice(externalSystems);
		for(Map.Entry<String, Integer> entry : items.entrySet()) {
			ItemDescription itemDesc = null;

			try {
				itemDesc = externalSystems.getInventorySystem().getItemDescription(entry.getKey());
			} catch (ItemNotFoundException e) {
				throw new IllegalStateException(e);
			}

			/* Remove the VAT applied in getTotalPrice. TODO: There really should be a more robust interface for this. */
			storeRevenue -= itemDesc.getPrice() * itemDesc.getTaxRate().toPercent() * entry.getValue();
		}

		/* The store can never lose money from a sale */
		if(storeRevenue < 0) {
			storeRevenue = 0;
		}

		for(SaleObserver observer : observers) {
			observer.onCustomerPayment(storeRevenue, amountPaid);
		}

		return receipt;
	}
}
