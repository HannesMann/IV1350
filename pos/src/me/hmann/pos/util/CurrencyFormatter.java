package me.hmann.pos.util;

import java.text.DecimalFormat;

/**
 * Used to format currency with appropriate precision.
 */
public class CurrencyFormatter {
	private static DecimalFormat decimalFormat;

	static {
		decimalFormat = new DecimalFormat();
		decimalFormat.setGroupingUsed(false);
		decimalFormat.setMaximumFractionDigits(2);
	}

	/**
	 * Format currency with correct amount of decimals and grouping.
	 * @param amount Amount of currency as a floating-point value.
	 * @return A string representing the amount.
	 */
	public static String format(double amount) {
		return decimalFormat.format(amount);
	}
}
