package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.internal.Printer;
import me.hmann.pos.model.dto.SaleDescription;

import java.time.LocalDateTime;

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
	 * @param amountPaid The amount the customer paid (not necessarily the same as the total price.
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
		Printer printer = systems.getPrinter();
		printer.startPrint();
		printer.printLine("BLA BLA BLA");
		printer.finishPrint();
	}
}
