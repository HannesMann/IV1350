package me.hmann.pos.integration.exceptions;

/**
 * Indicates that an error occurred when running a query on a database.
 * It's unlikely that the program can continue.
 *
 * This could be because:
 * - A connection could not be established to the database server.
 * - The database server is not running.
 * - The database server is overloaded.
 * - The query to the database was invalid.
 */
public class DatabaseException extends RuntimeException {
	private Object originatingObject;

	/**
	 * Create a new exception. The object that tried to query the database needs to indicate what error occurred.
	 * @param originatingObject The object that tried to query the database.
	 * @param message A description of the error that occurred when querying the database.
	 */
	public DatabaseException(Object originatingObject, String message) {
		super(message);
		this.originatingObject = originatingObject;
	}

	/**
	 * @return The object that tried to query the database.
	 */
	public Object getOriginatingObject() {
		return originatingObject;
	}
}
