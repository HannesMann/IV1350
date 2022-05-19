package me.hmann.pos.model.discounts;

import me.hmann.pos.integration.IntegrationSystems;
import me.hmann.pos.integration.exceptions.ItemNotFoundException;
import me.hmann.pos.model.dto.SaleDescription;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PercentageDiscountTest {
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
	void testDescriptions() {
		PercentageDiscount discountA = new PercentageDiscount("MEATB", 0.2);
		assertEquals("-20% Meatballs 1 kg", discountA.getDescription(systems));

		PercentageDiscount discountB = new PercentageDiscount("BROCO", 1.0);
		assertEquals("-100% Broccoli 1 kg", discountB.getDescription(systems));

		PercentageDiscount discountC = new PercentageDiscount("TORTL", 0.0);
		assertEquals("-0% Tortilla 8 pack", discountC.getDescription(systems));
	}

	@Test
	void testDiscountApplies() {
		PercentageDiscount discount = new PercentageDiscount("TOAST", 0.4);

		HashMap<String, Integer> validSaleMap1 = new HashMap<>();
		validSaleMap1.put("TOAST", 5);
		SaleDescription validSale1 = new SaleDescription(validSaleMap1, new ArrayList<>());

		assertTrue(discount.doesDiscountApply(systems, validSale1));

		HashMap<String, Integer> validSaleMap2 = new HashMap<>();
		validSaleMap2.put("TORTL", 2);
		validSaleMap2.put("MEATB", 10);
		validSaleMap2.put("TOAST", 1);
		SaleDescription validSale2 = new SaleDescription(validSaleMap2, new ArrayList<>());

		assertTrue(discount.doesDiscountApply(systems, validSale2));

		HashMap<String, Integer> invalidSaleMap1 = new HashMap<>();
		invalidSaleMap1.put("AAAAA", 5);
		SaleDescription invalidSale1 = new SaleDescription(invalidSaleMap1, new ArrayList<>());

		assertFalse(discount.doesDiscountApply(systems, invalidSale1));

		HashMap<String, Integer> invalidSaleMap2 = new HashMap<>();
		invalidSaleMap1.put("TORTL", 2);
		invalidSaleMap1.put("MEAT", 10);
		invalidSaleMap1.put("AAAAA", 1);
		SaleDescription invalidSale2 = new SaleDescription(invalidSaleMap2, new ArrayList<>());

		assertFalse(discount.doesDiscountApply(systems, invalidSale2));
	}

	@Test
	void testPriceReduction() throws ItemNotFoundException {
		PercentageDiscount discount = new PercentageDiscount("MEATB", 0.5);

		HashMap<String, Integer> smallReductionMap = new HashMap<>();
		smallReductionMap.put("MEATB", 1);
		SaleDescription smallReductionSale = new SaleDescription(smallReductionMap, new ArrayList<>());

		assertEquals(
			systems.getInventorySystem().getItemDescription("MEATB").getPriceWithVAT() * 0.5,
			discount.getPriceReduction(systems, smallReductionSale)
		);

		HashMap<String, Integer> bigReductionMap = new HashMap<>();
		bigReductionMap.put("MEATB", 10);
		SaleDescription bigReductionSale = new SaleDescription(bigReductionMap, new ArrayList<>());

		assertEquals(
			systems.getInventorySystem().getItemDescription("MEATB").getPriceWithVAT() * 5.0,
			discount.getPriceReduction(systems, bigReductionSale)
		);

		HashMap<String, Integer> noReductionMap = new HashMap<>();
		bigReductionMap.put("COLA3", 100);
		bigReductionMap.put("COLA1", 20);
		SaleDescription noReductionSale = new SaleDescription(noReductionMap, new ArrayList<>());

		assertEquals(0, discount.getPriceReduction(systems, noReductionSale));
	}
}