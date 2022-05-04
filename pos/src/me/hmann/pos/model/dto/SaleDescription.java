package me.hmann.pos.model.dto;

import me.hmann.pos.model.Discount;

import java.util.*;

public class SaleDescription {
	private SortedMap<ItemDescription, Integer> items;
	private ArrayList<Discount> discounts;

	SaleDescription(Map<ItemDescription, Integer> items, List<Discount> discounts) {

	}

	public Map<ItemDescription, Integer> getItems() {
		return items;
	}
	public List<Discount> getAppliedDiscounts() {
		return discounts;
	}

	public double getTotalPrice() {
		return 0;
	}
}
