package me.hmann.pos.integration.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseExceptionTest {
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