package me.hmann.pos.model;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.exceptions.ItemNotFoundException;
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
		assertEquals(0.0, sale.getRunningTotal(systems), "Sale is not empty when created");
	}

	@Test
	void testRecordItemReturnValue() throws ItemNotFoundException {
		OngoingSale sale = new OngoingSale();
		ItemDescription description = sale.recordItem(systems, "MEATB", 2);

		assertEquals(
			systems.getInventorySystem().getItemDescription("MEATB"),
			description,
			"Invalid return value from recordItem"
		);
	}

	@Test
	void testRecordItemWithQuantity() throws ItemNotFoundException {
		OngoingSale sale = new OngoingSale();
		sale.recordItem(systems, "MEATB", 2);

		assertEquals(
			systems.getInventorySystem().getItemDescription("MEATB").getPriceWithVAT() * 2,
			sale.getRunningTotal(systems),
			"Adding multiple items with quantity parameter does not work as expected"
		);
	}

	@Test
	void testRecordMultipleItems() throws ItemNotFoundException {
		OngoingSale sale = new OngoingSale();
		sale.recordItem(systems, "MEATB", 1);
		sale.recordItem(systems, "MEATB", 1);

		assertEquals(
			systems.getInventorySystem().getItemDescription("MEATB").getPriceWithVAT() * 2,
			sale.getRunningTotal(systems),
			"Adding multiple items by calling method twice does not work as expected"
		);
	}

	@Test
	void testRecordInvalidItem() {
		OngoingSale sale = new OngoingSale();

		/* An exception of type ItemNotFoundException is expected here. */
		assertThrows(
			ItemNotFoundException.class,
			() -> { sale.recordItem(systems, "AAAAA", 1); },
			"Adding invalid item did not throw exception"
		);
	}

	@Test
	void testRunningTotal() throws ItemNotFoundException {
		OngoingSale sale = new OngoingSale();
		sale.recordItem(systems, "SAUSG", 100);

		assertEquals(
			systems.getInventorySystem().getItemDescription("SAUSG").getPriceWithVAT() * 100,
			sale.getRunningTotal(systems),
			"Running total calculation does not work as expected"
		);
	}

	@Test
	void testCompleteSale() {
		OngoingSale sale = new OngoingSale();
		Sale finishedSale = sale.completeSale();

		assertEquals(
			sale.getRunningTotal(systems),
			finishedSale.getTotalPrice(systems),
			"Total price changed after sale completed"
		);
	}
}