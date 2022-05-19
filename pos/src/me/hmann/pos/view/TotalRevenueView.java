package me.hmann.pos.view;

import me.hmann.pos.model.Discount;
import me.hmann.pos.model.SaleObserver;
import me.hmann.pos.util.CurrencyFormatter;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Prints the total store revenue to the user interface.
 */
public class TotalRevenueView implements SaleObserver {
	private double totalRevenue;
	private double totalPayment;

	/**
	 * Create new observer starting at zero revenue.
	 */
	public TotalRevenueView() {
		totalRevenue = 0;
		totalPayment = 0;
	}

	@Override
	public void onItemQuantityIncreased(String itemId, int newQuantity) {

	}

	@Override
	public void onDiscountsApplied(List<Discount> discounts) {

	}

	@Override
	public void onCustomerPayment(double revenue, double payment) {
		totalRevenue += revenue;
		totalPayment += payment;

		System.out.println();
		System.out.println("Revenue report:");
		System.out.println("  Customers have paid " + CurrencyFormatter.format(totalPayment) + " kr since the program started.");
		System.out.println("  Excluding taxes and change, the store has received " + CurrencyFormatter.format(totalRevenue) + " kr from customers.");
		System.out.println();
	}
}
