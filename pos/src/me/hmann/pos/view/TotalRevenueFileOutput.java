package me.hmann.pos.view;

import me.hmann.pos.model.Discount;
import me.hmann.pos.model.SaleObserver;
import me.hmann.pos.util.CurrencyFormatter;
import me.hmann.pos.util.FileLog;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Prints the total store revenue to revenue.log.
 */
public class TotalRevenueFileOutput implements SaleObserver {
	private double totalRevenue;
	private double totalPayment;

	private final static String FILENAME = "revenue.log";
	private FileLog log;

	/**
	 * Create new observer starting at zero revenue.
	 */
	public TotalRevenueFileOutput() {
		totalRevenue = 0;
		totalPayment = 0;

		try {
			log = new FileLog(FILENAME);
			log.printMessage("");
		} catch (IOException e) {
			System.out.println("WARNING FOR DEVELOPERS: File revenue.log could not be created.");
			System.out.println("Program will continue without logging revenue to file.");
			System.out.println();
		}
	}

	/**
	 * Finish logging.
	 */
	public void finish() {
		if(log == null) return;
		log.finish();
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

		if(log == null) return;

		log.printMessage("Revenue report:");
		log.printMessage("  Customers have paid " + CurrencyFormatter.format(totalPayment) + " kr since the program started.");
		log.printMessage("  Excluding taxes and change, the store has received " + CurrencyFormatter.format(totalRevenue) + " kr from customers.");
		log.printMessage("");
	}
}
