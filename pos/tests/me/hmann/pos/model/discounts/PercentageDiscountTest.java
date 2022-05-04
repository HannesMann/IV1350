package me.hmann.pos.model.discounts;

import me.hmann.pos.integration.IntegrationSystems;
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
		assertEquals(discountA.getDescription(systems), "-20% Meatballs 1 kg");

		PercentageDiscount discountB = new PercentageDiscount("BROCO", 1.0);
		assertEquals(discountB.getDescription(systems), "-100% Broccoli 1 kg");

		PercentageDiscount discountC = new PercentageDiscount("TORTL", 0.0);
		assertEquals(discountC.getDescription(systems), "-0% Tortilla 8 pack");
	}

	@Test
	void testDiscountApplies() {
		PercentageDiscount discount = new PercentageDiscount("TOAST", 0.4);

		HashMap<String, Integer> validSaleMap1 = new HashMap<>();
		validSaleMap1.put("TOAST", 5);
		SaleDescription validSale1 = new SaleDescription(validSaleMap1, new ArrayList<>());

		assertEquals(discount.doesDiscountApply(systems, validSale1), true);

		HashMap<String, Integer> validSaleMap2 = new HashMap<>();
		validSaleMap1.put("TORTL", 2);
		validSaleMap1.put("MEATB", 10);
		validSaleMap1.put("TOAST", 1);
		SaleDescription validSale2 = new SaleDescription(validSaleMap2, new ArrayList<>());

		assertEquals(discount.doesDiscountApply(systems, validSale2), true);

		HashMap<String, Integer> invalidSaleMap1 = new HashMap<>();
		invalidSaleMap1.put("AAAAA", 5);
		SaleDescription invalidSale1 = new SaleDescription(invalidSaleMap1, new ArrayList<>());

		assertEquals(discount.doesDiscountApply(systems, invalidSale1), false);

		HashMap<String, Integer> invalidSaleMap2 = new HashMap<>();
		invalidSaleMap1.put("TORTL", 2);
		invalidSaleMap1.put("MEAT", 10);
		invalidSaleMap1.put("AAAAA", 1);
		SaleDescription invalidSale2 = new SaleDescription(invalidSaleMap2, new ArrayList<>());

		assertEquals(discount.doesDiscountApply(systems, invalidSale2), false);
	}

	@Test
	void testPriceReduction() {
		PercentageDiscount discount = new PercentageDiscount("MEATB", 0.5);

		HashMap<String, Integer> smallReductionMap = new HashMap<>();
		smallReductionMap.put("MEATB", 1);
		SaleDescription smallReductionSale = new SaleDescription(smallReductionMap, new ArrayList<>());

		assertEquals(
			discount.getPriceReduction(systems, smallReductionSale),
			systems.getInventorySystem().getItemDescription("MEATB").getPriceWithVAT() * 0.5
		);

		HashMap<String, Integer> bigReductionMap = new HashMap<>();
		bigReductionMap.put("MEATB", 10);
		SaleDescription bigReductionSale = new SaleDescription(bigReductionMap, new ArrayList<>());

		assertEquals(
			discount.getPriceReduction(systems, bigReductionSale),
			systems.getInventorySystem().getItemDescription("MEATB").getPriceWithVAT() * 5.0
		);

		HashMap<String, Integer> noReductionMap = new HashMap<>();
		bigReductionMap.put("COLA3", 100);
		bigReductionMap.put("COLA1", 20);
		SaleDescription noReductionSale = new SaleDescription(noReductionMap, new ArrayList<>());

		assertEquals(discount.getPriceReduction(systems, noReductionSale), 0);
	}
}