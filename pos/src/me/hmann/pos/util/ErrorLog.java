package me.hmann.pos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Responsible for logging runtime errors that cannot be handled gracefully in the view.
 */
public class ErrorLog extends FileLog {
	private final static String FILENAME = "errors.log";

	/**
	 * Create a new error log, which will log to the file "error-log.txt" in the current directory.
	 */
	public ErrorLog() throws IOException {
		super(FILENAME);
	}

	/**
	 * Print an exception, including stack trace, to the log.
	 * @param exception The exception.
	 */
	public void printException(Exception exception) {
		printMessage("Unhandled exception caught.");

		StringWriter stringWriter = new StringWriter();
		PrintWriter tempWriter = new PrintWriter(stringWriter);

		exception.printStackTrace(tempWriter);
		tempWriter.flush();

		for(String line : stringWriter.toString().split(System.lineSeparator())) {
			printMessage(line);
		}

		tempWriter.close();
	}
}
