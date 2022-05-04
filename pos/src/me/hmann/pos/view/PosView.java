package me.hmann.pos.view;

import me.hmann.pos.controller.PosController;
import me.hmann.pos.model.dto.ItemDescription;

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

	private void presentSaleStatus(ItemDescription latestItem) {
		if(latestItem != null) {
			int vat = (int)(latestItem.getTaxRate() * 100);
			System.out.println(latestItem.getName() + " - " + latestItem.getPriceWithVAT() + " kr (VAT " + vat + "%)");
		}

		System.out.println("Running total: " + controller.getCurrentRunningTotal() + " kr");
	}

	/**
	 * Runs the point-of-sale system by simulating a cashier, with a command line interface.
	 */
	public void simulateCashier() {
		controller.startSale();
		System.out.println("New sale started");

		/* Used to read lines from System.in */
		Scanner scanner = new Scanner(System.in);

		boolean itemsLeft = true;
		while(itemsLeft) {
			System.out.print("  Enter the next item, or nothing if all goods recorded: ");
			String itemId = scanner.nextLine().toUpperCase().trim();

			if(itemId.equals("")) {
				itemsLeft = false;
			}
			else {
				System.out.print("  Item " + itemId + ", enter quantity: ");
				int quantity = Integer.parseInt(scanner.nextLine());
				ItemDescription description = controller.recordItem(itemId, quantity);

				if(description == null) {
					System.out.println("  No such item exists!");
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
			System.out.print("  Enter customer ID: ");
			String customerId = scanner.nextLine().toUpperCase().trim();

			controller.applyCustomerDiscounts(customerId);
			System.out.println("  " + controller.getAppliedDiscounts().size() + " discount(s) applied to sale.");
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

		System.out.println("Change to give back to customer: " + change + " kr");
	}
}
