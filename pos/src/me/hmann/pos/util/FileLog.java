package me.hmann.pos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Used for logging messages to a file.
 */
public class FileLog {
	private PrintWriter printWriter;

	/**
	 * Create a new error log, which will log to the specified file.
	 * @param filename The file to create and use for logging.
	 */
	public FileLog(String filename) throws IOException {
		printWriter = new PrintWriter(new FileWriter(filename), false);
		printMessage("Started logging.");
	}

	/**
	 * Print a generic message to the log.
	 * @param message The message.
	 */
	public void printMessage(String message) {
		if(printWriter != null) {
			printWriter.print("[" + dateAndTimeAsString() + "] ");
			printWriter.println(message);
		}
	}

	/**
	 * Indicate that the program is finished with this object and logging should stop.
	 */
	public void finish() {
		printMessage("Finished logging.");

		printWriter.flush();
		printWriter.close();
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
