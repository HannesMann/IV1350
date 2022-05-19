package me.hmann.pos.startup;

import me.hmann.pos.controller.PosController;
import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.util.ErrorLog;
import me.hmann.pos.view.PosView;

import java.io.IOException;

/**
 * Initializes and starts the POS (point-of-sale) system.
 */
public class Startup {
	/**
	 * Main method that simulates a run of the program.
	 * @param args Command line arguments.
	 */
    public static void main(String[] args) {
		/*
		 * The debug log could possibly fit better in the view,
		 * but this is the only class that has access to everything in the program.
		 */
		ErrorLog debugLog = null;

		try {
			debugLog = new ErrorLog();
		} catch(IOException e) {
			System.out.println("WARNING FOR DEVELOPERS: File errors.log could not be created.");
			System.out.println("Program will continue without logging errors.");
			System.out.println();
		}

		try {
			IntegrationSystems systems = new IntegrationSystems();
			PosController controller = new PosController(systems);
			PosView view = new PosView(controller);

			view.simulateCashier();
		}
		catch(Exception exception) {
			if(debugLog != null) {
				debugLog.printException(exception);
			}
		}

		if(debugLog != null) {
			debugLog.finish();
		}
    }
}