package me.hmann.pos.model.dto;

/**
 * Tax/VAT for an item. Only fixed amounts of VAT can be applied to items.
 */
public enum TaxRate {
	/**
	 * Lowest VAT, 6%.
	 */
	RATE_6,
	/**
	 * Medium VAT, 12%.
	 */
	RATE_12,
	/**
	 * Highest VAT, 25%.
	 */
	RATE_25;

	/**
	 * Convert an enum value to a double.
	 * @return The percentage to add to the price of an item.
	 */
	public double toPercent() {
		switch(this) {
			case RATE_6:
				return 0.06;
			case RATE_12:
				return 0.12;
			case RATE_25:
				return 0.25;
			default:
				return 0.00;
		}
	}

	/**
	 * Convert an enum value to a string.
	 * @return The description of this specific tax rate.
	 */
	@Override
	public String toString() {
		switch(this) {
			case RATE_6:
				return "6%";
			case RATE_12:
				return "12%";
			case RATE_25:
				return "25%";
			default:
				return "0%";
		}
	}
}
