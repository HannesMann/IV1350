package me.hmann.pos.integration.exceptions;

import me.hmann.pos.integration.IntegrationSystems;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseExceptionTest {
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
	void testExceptionIsThrown() {
		assertThrows(
			DatabaseException.class,
			() -> { systems.getInventorySystem().getItemDescription("COLA100"); },
			"Item COLA100 did not throw database exception"
		);

		assertThrows(
			DatabaseException.class,
			() -> { systems.getInventorySystem().getItemDescription("COLA200"); },
			"Item COLA200 did not throw database exception"
		);
	}

	@Test
	void testExceptionValues() {
		try {
			throw new DatabaseException(this, "Database error test");
		}
		catch(DatabaseException exception) {
			assertEquals(
				this,
				exception.getOriginatingObject(),
				"Exception does not propagate correct originating object"
			);

			assertEquals(
				"Database error test",
				exception.getMessage(),
				"Exception does not propagate correct message"
			);
		}
	}
}