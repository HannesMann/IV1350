package me.hmann.pos.integration.external;

import me.hmann.pos.model.Discount;
import me.hmann.pos.model.discounts.PercentageDiscount;
import me.hmann.pos.model.discounts.XForYDiscount;

/**
 * Keeps track of discounts for different customers.
 */
public class DiscountRegistry {
	/* Some example discounts for seminar */
	private final static Discount threeForTwoSausage = new XForYDiscount("SAUSG", 3, 2);
	private final static Discount twoForOneCola3 = new XForYDiscount("COLA3", 2, 1);
	private final static Discount twoForOneCola5 = new XForYDiscount("COLA5", 2, 1);
	private final static Discount twentyPotato = new PercentageDiscount("POTAT", 0.2);
	private final static Discount fiftyTortilla = new PercentageDiscount("TORTL", 0.5);
	private final static Discount tenToast = new PercentageDiscount("TOAST", 0.1);

	/**
	 * Get applicable discounts for a customer, if any.
	 * @param id The ID the customer has specified.
	 * @return A list of discounts (can be empty).
	 */
	public Discount[] getDiscountsForCustomer(int id) {
		switch(id) {
			case 3581:
				return new Discount[] { threeForTwoSausage, twentyPotato, tenToast };
			case 8536:
				return new Discount[] { twoForOneCola3, twoForOneCola5, fiftyTortilla };
			case 9977:
				return new Discount[] { twentyPotato, fiftyTortilla, tenToast };
			case 1210:
				return new Discount[] { twoForOneCola5, tenToast };
			default:
				return null;
		}
	}
}
