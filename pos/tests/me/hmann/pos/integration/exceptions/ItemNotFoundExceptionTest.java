package me.hmann.pos.integration.exceptions;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.external.InventorySystem;
import me.hmann.pos.model.OngoingSale;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemNotFoundExceptionTest {
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
	void testExceptionDescription() {
		ItemNotFoundException exception1 = new ItemNotFoundException(systems.getInventorySystem(), "AAAAA");
		assertEquals(
			"An item with the ID \"AAAAA\" was not found in the store inventory.",
			exception1.getMessage(),
			"Exception message formatting was incorrect"
		);

		ItemNotFoundException exception2 = new ItemNotFoundException(systems.getInventorySystem(), "XYZABC");
		assertEquals(
			"An item with the ID \"XYZABC\" was not found in the store inventory.",
			exception2.getMessage(),
			"Exception message formatting was incorrect"
		);

		ItemNotFoundException exception3 = new ItemNotFoundException(systems.getInventorySystem(), "ÅÄÖ\".-;");
		assertEquals(
			"An item with the ID \"ÅÄÖ\".-;\" was not found in the store inventory.",
			exception3.getMessage(),
			"Exception message formatting was incorrect"
		);
	}

	@Test
	void testExceptionValues() {
		ItemNotFoundException exception = new ItemNotFoundException(systems.getInventorySystem(), "AAAAA");

		assertEquals(
			systems.getInventorySystem(),
			exception.getInventory(),
			"Exception does not propagate correct inventory system"
		);
		assertEquals(
			"AAAAA",
			exception.getItemId(),
			"Exception does not propagate correct item ID"
		);
	}
}