package me.hmann.pos.integration.internal;

import java.util.ArrayList;

/***
 * Responsible for printing receipts to the customer.
 * The public interface is very generic if changes need to be made in the future.
 */
public class Printer {
	private ArrayList<String> printedLines;

	/***
	 * Starts printing a new receipt.
	 */
	public void startPrint() {
		printedLines = new ArrayList<>();
	}

	/***
	 * Finishes printing the receipt.
	 * The receipt will contain all lines written since Printer.startPrint was called.
	 */
	public void finishPrint() {
		/* This would normally go to the printer, but we show it on the command line */
		for(String line : printedLines) {
			System.out.println("  " + line);
		}
	}

	/***
	 * Prints a text line on the receipt.
	 * @param line The line to print.
	 */
	public void printLine(String line) {
		printedLines.add(line);
	}
}
