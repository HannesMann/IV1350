package me.hmann.pos.view;

import me.hmann.pos.controller.PosController;
import java.util.Random;

/**
 * Implements the view the cashier uses to interact with the point-of-sale system.
 */
public class PosView {
	private PosController controller;
	/* This random generator will be used as the source for the decisions made during a simulation of the program. */
	private Random decisionMaker;

	/**
	 * Initialize the point-of-sale cashier view.
	 * @param controller The controller interface.
	 */
	public PosView(PosController controller) {
		this.controller = controller;
		this.decisionMaker = new Random();
	}

	/**
	 * Runs the point-of-sale system by simulating a cashier, making random decisions each time.
	 */
	public void simulateCashier() {
		controller.startSale();
	}
}
