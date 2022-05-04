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

	public IntegrationSystems() {

	}

	public AccountingSystem getAccountingSystem() {
		return accountingSystem;
	}

	public InventorySystem getInventorySystem() {
		return inventorySystem;
	}

	public DiscountRegistry getDiscountRegistry() {
		return discountRegistry;
	}

	public CashRegister getCashRegister() {
		return register;
	}

	public Printer getPrinter() {
		return printer;
	}

	public SaleLog getSaleLog() {
		return saleLog;
	}
}
