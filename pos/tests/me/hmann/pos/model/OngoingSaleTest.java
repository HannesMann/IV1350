package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.model.dto.ItemDescription;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OngoingSaleTest {
	private IntegrationSystems systems;

	@BeforeEach
	void setUp() {
		systems = new IntegrationSystems();
	}

	@AfterEach
	void finish() {
		systems = null;
	}

	@Test
	void testNewSale() {
		OngoingSale sale = new OngoingSale();
		assertEquals(sale.getRunningTotal(systems), 0.0, "Sale is not empty when created");
	}

	@Test
	void testRecordItemReturnValue() {
		OngoingSale sale = new OngoingSale();
		ItemDescription description = sale.recordItem(systems, "MEATB", 2);

		assertEquals(
			description,
			systems.getInventorySystem().getItemDescription("MEATB"),
			"Invalid return value from recordItem");
	}

	@Test
	void testRecordItemWithQuantity() {
		OngoingSale sale = new OngoingSale();
		sale.recordItem(systems, "MEATB", 2);

		assertEquals(
			sale.getRunningTotal(systems),
			systems.getInventorySystem().getItemDescription("MEATB").getPriceWithVAT() * 2,
			"Adding multiple items with quantity parameter does not work as expected");
	}

	@Test
	void testRecordMultipleItems() {
		OngoingSale sale = new OngoingSale();
		sale.recordItem(systems, "MEATB", 1);
		sale.recordItem(systems, "MEATB", 1);

		assertEquals(
			sale.getRunningTotal(systems),
			systems.getInventorySystem().getItemDescription("MEATB").getPriceWithVAT() * 2,
			"Adding multiple items by calling method twice does not work as expected");
	}

	@Test
	void testRecordInvalidItem() {
		OngoingSale sale = new OngoingSale();
		ItemDescription description = sale.recordItem(systems, "AAAAA", 1);

		assertEquals(
			description,
			null,
			"Adding invalid item does not work as expected");
	}

	@Test
	void testRunningTotal() {
		OngoingSale sale = new OngoingSale();
		sale.recordItem(systems, "SAUSG", 100);

		assertEquals(
			sale.getRunningTotal(systems),
			systems.getInventorySystem().getItemDescription("SAUSG").getPriceWithVAT() * 100,
			"Running total calculation does not work as expected");
	}

	@Test
	void testCompleteSale() {
		OngoingSale sale = new OngoingSale();
		Sale finishedSale = sale.completeSale();

		assertEquals(
			sale.getRunningTotal(systems),
			finishedSale.getTotalPrice(systems),
			"Total price changed after sale completed");
	}
}