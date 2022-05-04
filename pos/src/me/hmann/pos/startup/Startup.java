package me.hmann.pos.startup;

import me.hmann.pos.controller.PosController;
import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.view.PosView;

/**
 * Initializes and starts the POS (point-of-sale) system.
 */
public class Startup {
    public static void main(String[] args) {
		IntegrationSystems systems = new IntegrationSystems();
		PosController controller = new PosController(systems);
		PosView view = new PosView(controller);

		view.simulateCashier();
    }
}