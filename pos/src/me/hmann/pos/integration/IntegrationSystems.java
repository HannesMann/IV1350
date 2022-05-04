package me.hmann.pos.integration;

import me.hmann.pos.integration.external.AccountingSystem;
import me.hmann.pos.integration.external.DiscountRegistry;
import me.hmann.pos.integration.external.InventorySystem;
import me.hmann.pos.integration.internal.CashRegister;
import me.hmann.pos.integration.internal.Printer;
import me.hmann.pos.integration.internal.SaleLog;

/***
 * Stores all internal and external systems in a single class for easier access.
 */
public class IntegrationSystems {
	private AccountingSystem accountingSystem;
	private InventorySystem inventorySystem;
	private DiscountRegistry discountRegistry;

	private CashRegister register;
	private Printer printer;
	private SaleLog saleLog;

	/**
	 * Initialize all internal and external systems.
	 */
	public IntegrationSystems() {
		accountingSystem = new AccountingSystem();
		inventorySystem = new InventorySystem();
		discountRegistry = new DiscountRegistry();

		register = new CashRegister();
		printer = new Printer();
		saleLog = new SaleLog();
	}

	/**
	 * @return The external accounting system.
	 */
	public AccountingSystem getAccountingSystem() {
		return accountingSystem;
	}

	/**
	 * @return The external inventory system.
	 */
	public InventorySystem getInventorySystem() {
		return inventorySystem;
	}

	/**
	 * @return The external discount system.
	 */
	public DiscountRegistry getDiscountRegistry() {
		return discountRegistry;
	}

	/**
	 * @return The internal register system.
	 */
	public CashRegister getCashRegister() {
		return register;
	}

	/**
	 * @return The internal printer interface.
	 */
	public Printer getPrinter() {
		return printer;
	}

	/**
	 * @return The internal sale logger.
	 */
	public SaleLog getSaleLog() {
		return saleLog;
	}
}
