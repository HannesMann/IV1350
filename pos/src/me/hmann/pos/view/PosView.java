package me.hmann.pos.view;

import me.hmann.pos.controller.PosController;
import me.hmann.pos.integration.exceptions.ItemNotFoundException;
import me.hmann.pos.model.dto.ItemDescription;

import java.text.DecimalFormat;
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
			System.out.println("Running total: " + formatMoney(controller.getCurrentRunningTotal()) + " kr");
		}
		else {
			System.out.println("Total price: " + formatMoney(controller.getCurrentRunningTotal()) + " kr");
		}
	}

	/**
	 * Runs the point-of-sale system by simulating a cashier, with a command line interface.
	 */
	public void simulateCashier() {
		try {
			System.out.println("Available items - SAUSG MEATB POTAT BROCO BREAD TOAST TORTL COLA3 COLA5 COLA1");
			System.out.println("Customers with discounts - 3581 8536 9977 1210");
			System.out.println();
			System.out.println("Ask for COLA100 or COLA200 to simulate database failure.");
			System.out.println();

			controller.startSale();
			System.out.println("Sale started");

			/* Used to read lines from System.in */
			Scanner scanner = new Scanner(System.in);

			boolean itemsLeft = true;
			while (itemsLeft) {
				System.out.print("Enter the next item, or nothing to end sale: ");
				String itemId = scanner.nextLine().toUpperCase().trim();

				if (itemId.equals("")) {
					itemsLeft = false;
				} else {
					System.out.print("Item " + itemId + ", enter quantity: ");
					try {
						int quantity = Integer.parseInt(scanner.nextLine());

						try {
							presentSaleStatus(controller.recordItem(itemId, quantity));
						} catch (ItemNotFoundException exception) {
							System.out.println("An item with ID \"" + exception.getItemId() + "\" does not exist.");
						}
					} catch (NumberFormatException exception) {
						System.out.println("An invalid quantity was specified.");
					}
				}

				System.out.println();
			}

			controller.endSale();

			System.out.println("Sale finished");
			presentSaleStatus(null);
			System.out.println();

			System.out.print("Does the customer want to apply discounts (Y/N)? ");
			if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
				System.out.print("Enter customer ID: ");

				try {
					int customerId = Integer.parseInt(scanner.nextLine().trim());

					int discounts = controller.applyCustomerDiscounts(customerId);
					System.out.println("" + discounts + " discount(s) applied to sale.");
					System.out.println();

					presentSaleStatus(null);
				} catch (NumberFormatException exception) {
					System.out.println("An invalid customer ID was specified. No discounts could be applied.");
				}

				System.out.println();
			}

			double customerPayment = 0;

			/* Repeat until correct payment has been specified. Sale cannot be ended without payment. */
			while (true) {
				System.out.print("Enter amount the customer paid: ");

				try {
					customerPayment = Double.parseDouble(scanner.nextLine().trim());
					if (customerPayment > 0) {
						break;
					} else {
						System.out.println("An invalid payment amount was specified. Please try again.");
					}
				} catch (NumberFormatException exception) {
					System.out.println("An invalid payment amount was specified. Please try again.");
				}
			}

			System.out.println("Sale completed. Printing receipt.");
			System.out.println();

			double change = controller.payAndPrintReceipt(customerPayment);
			System.out.println();

			System.out.println("Change to give back to customer: " + formatMoney(change) + " kr");
		}
		catch(Exception exception) {
			System.out.println("A fatal program error has occurred and the sale cannot be continued.");
			System.out.println("For developers: See errors.log for more information.");

			/* Propagate exception to error log. */
			throw exception;
		}
	}
}
