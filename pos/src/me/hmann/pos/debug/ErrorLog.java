package me.hmann.pos.debug;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Responsible for logging runtime errors that cannot be handled gracefully in the view.
 */
public class ErrorLog {
	private final static String FILENAME = "errors.log";

	private PrintWriter printWriter;

	/**
	 * Create a new error log, which will log to the file "error-log.txt" in the current directory.
	 */
	public ErrorLog() throws IOException {
		printWriter = new PrintWriter(new FileWriter(FILENAME), false);
		printMessage("Started logging.");
	}

	/**
	 * Print a generic message to the log.
	 * @param message The message.
	 */
	public void printMessage(String message) {
		if(printWriter != null) {
			printWriter.print(dateAndTimeAsString() + " - ");
			printWriter.println(message);
		}
	}

	/**
	 * Print an exception, including stack trace, to the log.
	 * @param exception The exception.
	 */
	public void printException(Exception exception) {
		printMessage("Unhandled exception caught.");
		if(printWriter != null) {
			exception.printStackTrace(printWriter);
		}
	}

	/**
	 * Indicate that the program is finished and logging should stop.
	 */
	public void finish() {
		printMessage("Finished logging.");

		printWriter.flush();
		printWriter = null;
	}

	private String dateAndTimeAsString() {
		LocalDateTime dateAndTime = LocalDateTime.now();

		/* Nanoseconds give too much precision */
		String result = DateTimeFormatter.ISO_LOCAL_DATE.format(dateAndTime.withNano(0));
		result += " ";
		result += DateTimeFormatter.ISO_LOCAL_TIME.format(dateAndTime.withNano(0));

		return result;
	}
}
