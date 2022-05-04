package me.hmann.pos.model;

import me.hmann.pos.integration.internal.Printer;
import me.hmann.pos.model.dto.SaleDescription;

import java.time.LocalDateTime;

public class Receipt {
	private SaleDescription saleDescription;

	private LocalDateTime dateAndTime;
	private double amountPaid;

	Receipt(SaleDescription description, double amountPaid) {
		saleDescription = description;
		this.amountPaid = amountPaid;
	}

	public SaleDescription getSale() {
		return saleDescription;
	}

	public LocalDateTime getSaleTime() {
		return dateAndTime;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public double getChange() {
		return amountPaid - saleDescription.getTotalPrice();
	}

	/***
	 * Prints this receipt to a printer.
	 * @param printer The printer to print on.
	 */
	public void print(Printer printer) {
		printer.startPrint();
		printer.printLine("BLA BLA BLA");
		printer.finishPrint();
	}
}
