package me.hmann.pos.view;

import me.hmann.pos.controller.PosController;
import me.hmann.pos.integration.external.InventorySystem;
import me.hmann.pos.model.dto.ItemDescription;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements the view the cashier uses to interact with the point-of-sale system.
 */
public class PosView {
	private PosController controller;

	/**
	 * Initialize the point-of-sale cashier view.
	 * @param controller The controller interface.
	 */
	public PosView(PosController controller) {
		this.controller = controller;
	}

	private String formatMoney(double amount) {
		DecimalFormat df = new DecimalFormat();
		df.setGroupingUsed(false);
		df.setMaximumFractionDigits(2);

		return df.format(amount);
	}

	private void presentSaleStatus(ItemDescription latestItem) {
		if(latestItem != null) {
			System.out.println("\"" + latestItem.getName() + "\" - " + formatMoney(latestItem.getPriceWithVAT()) + " kr (VAT " + latestItem.getTaxRate() + ")");
		}

		System.out.println("Running total: " + formatMoney(controller.getCurrentRunningTotal()) + " kr");
	}

	/**
	 * Runs the point-of-sale system by simulating a cashier, with a command line interface.
	 */
	public void simulateCashier() {
		/* The view shouldn't be using classes from the integration layer, but this is only to make testing easier. */
		System.out.print("Available items -");
		for(String item : InventorySystem.AVAILABLE_ITEMS.keySet()) {
			System.out.print(" " + item);
		}
		System.out.println();
		System.out.println();

		controller.startSale();
		System.out.println("New sale started");

		/* Used to read lines from System.in */
		Scanner scanner = new Scanner(System.in);

		boolean itemsLeft = true;
		while(itemsLeft) {
			System.out.print("Enter the next item, or nothing if all goods recorded: ");
			String itemId = scanner.nextLine().toUpperCase().trim();

			if(itemId.equals("")) {
				itemsLeft = false;
			}
			else {
				System.out.print("Item " + itemId + ", enter quantity: ");
				int quantity = Integer.parseInt(scanner.nextLine());
				ItemDescription description = controller.recordItem(itemId, quantity);

				if(description == null) {
					System.out.println("No such item exists!");
				}
				else {
					presentSaleStatus(description);
				}
			}

			System.out.println();
		}

		controller.endSale();

		System.out.println("Sale ended");
		presentSaleStatus(null);
		System.out.println();

		System.out.print("Does the customer want to apply discounts (Y/N)? ");
		if(scanner.nextLine().trim().equalsIgnoreCase("Y")) {
			System.out.print("Enter customer ID: ");
			String customerId = scanner.nextLine().toUpperCase().trim();

			controller.applyCustomerDiscounts(customerId);
			System.out.println("" + controller.getAppliedDiscounts().size() + " discount(s) applied to sale.");
			System.out.println();

			presentSaleStatus(null);
			System.out.println();
		}

		System.out.print("Enter amount the customer paid: ");
		double customerPayment = Double.parseDouble(scanner.nextLine());

		System.out.println("Sale completed. Printing receipt.");
		System.out.println();

		double change = controller.payAndPrintReceipt(customerPayment);
		System.out.println();

		System.out.println("Change to give back to customer: " + formatMoney(change) + " kr");
	}
}
