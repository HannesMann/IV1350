package me.hmann.pos.integration.internal;

/***
 * Responsible for printing receipts to the customer.
 * The public interface is very generic if changes need to be made in the future.
 */
public class Printer {
	/***
	 * Starts printing a new receipt.
	 */
	public void startPrint() {

	}

	/***
	 * Finishes printing the receipt.
	 * The receipt will contain all lines written since Printer.startPrint was called.
	 */
	public void finishPrint() {

	}

	/***
	 * Prints a text line on the receipt.
	 * @param line The line to print.
	 */
	public void printLine(String line) {

	}
}
