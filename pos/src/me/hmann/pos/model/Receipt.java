package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.exceptions.ItemNotFoundException;
import me.hmann.pos.integration.internal.Printer;
import me.hmann.pos.model.dto.ItemDescription;
import me.hmann.pos.model.dto.SaleDescription;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;

/**
 * A receipt containing all information about a completed sale.
 */
public class Receipt {
	private SaleDescription saleDescription;

	private LocalDateTime dateAndTime;
	private double amountPaid;

	/**
	 * Create a receipt from a sale.
	 * @param description Description of the sale.
	 * @param amountPaid The amount the customer paid (not necessarily the same as the total price).
	 */
	Receipt(SaleDescription description, double amountPaid) {
		this.saleDescription = description;
		this.dateAndTime = LocalDateTime.now();
		this.amountPaid = amountPaid;
	}

	/**
	 * @return A description of the sale made by the customer.
	 */
	public SaleDescription getSale() {
		return saleDescription;
	}

	/**
	 * @return The time the sale was completed, in the local time zone.
	 */
	public LocalDateTime getSaleTime() {
		return dateAndTime;
	}

	/**
	 * @return The amount the customer paid.
	 */
	public double getAmountPaid() {
		return amountPaid;
	}
	/**
	 * @param systems External systems needed to retrieve price.
	 * @return The amount the customer should get back.
	 */
	public double getChange(IntegrationSystems systems) {
		return amountPaid - saleDescription.getTotalPrice(systems);
	}

	/***
	 * Prints this receipt to a printer.
	 * @param systems External systems needed to retrieve item information and printer.
	 */
	public void print(IntegrationSystems systems) {
		/* Used to format currency */
		DecimalFormat df = new DecimalFormat();
		df.setGroupingUsed(false);
		df.setMaximumFractionDigits(2);

		Printer printer = systems.getPrinter();
		printer.startPrint();

		printer.printLine("HANNES SUPERMARKET");
		printer.printLine("OPEN ALL DAYS 8-22");
		printer.printLine("");

		/* Nanoseconds give too much precision */
		String dateString = DateTimeFormatter.ISO_LOCAL_DATE.format(dateAndTime.withNano(0));
		dateString += " ";
		dateString += DateTimeFormatter.ISO_LOCAL_TIME.format(dateAndTime.withNano(0));

		printer.printLine(dateString);
		printer.printLine("");

		printer.printLine("Items");
		printer.printLine("----------");
		for(Entry<String, Integer> item : saleDescription.getItems().entrySet()) {
			ItemDescription itemDesc = null;

			try {
				itemDesc = systems.getInventorySystem().getItemDescription(item.getKey());
			} catch (ItemNotFoundException e) {
				throw new IllegalStateException(e);
			}

			printer.printLine(itemDesc.getName() + " (" + item.getValue() + ") - " + df.format(itemDesc.getPriceWithVAT()) + " kr (VAT " + itemDesc.getTaxRate() + ")");
		}
		printer.printLine("----------");
		printer.printLine("");

		if(!saleDescription.getAppliedDiscounts().isEmpty()) {
			printer.printLine("Discounts");
			printer.printLine("----------");
			for(Discount discount : saleDescription.getAppliedDiscounts()) {
				double reduction = discount.getPriceReduction(systems, saleDescription);
				printer.printLine(discount.getDescription(systems) + " - " + df.format(reduction) + " kr");
			}
			printer.printLine("----------");
			printer.printLine("");
		}

		printer.printLine("Total: " + df.format(saleDescription.getTotalPrice(systems)) + " kr");

		double vatPaid = 0;
		for(Entry<String, Integer> item : saleDescription.getItems().entrySet()) {
			ItemDescription itemDesc = null;

			try {
				itemDesc = systems.getInventorySystem().getItemDescription(item.getKey());
			} catch (ItemNotFoundException e) {
				throw new IllegalStateException(e);
			}

			/* Calculate difference between price with and without VAT */
			vatPaid += (itemDesc.getPriceWithVAT() - itemDesc.getPrice()) * item.getValue();
		}

		printer.printLine("VAT: " + df.format(vatPaid) + " kr");
		printer.printLine("");
		printer.printLine("Amount paid: " + df.format(amountPaid) + " kr");
		printer.printLine("Change: " + df.format(getChange(systems)) + " kr");

		printer.finishPrint();
	}
}
