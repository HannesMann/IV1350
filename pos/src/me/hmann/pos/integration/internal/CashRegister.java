package me.hmann.pos.integration.internal;

/***
 * Keeps track of cash in the register.
 */
public class CashRegister {
	private double cash = 0;

	/**
	 * @return The amount of money present in the register.
	 */
	public double getAvailableCash() {
		return cash;
	}

	/**
	 * Indicate that money has been deposited in the cash register.
	 */
	public void depositCash(double amount) {

	}
}
