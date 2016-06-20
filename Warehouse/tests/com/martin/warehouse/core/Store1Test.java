package com.martin.warehouse.core;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.martin.warehouse.core.Store1;
import com.martin.warehouse.dao.WarehouseConnection;
import com.martin.warehouse.item.Item;
import com.martin.warehouse.item.SoldItem;

public class Store1Test {

	private Store1 s1;

	@Before
	public void setUp() {
		WarehouseConnection.getInstance().dropTables();
		s1 = new Store1();
	}

	@Test
	public void addTest() throws SQLException {
		Item item1 = new Item();
		item1.setId(1);
		item1.setQuantity(1);
		Item item2 = new Item();
		item2.setId(2);
		item2.setQuantity(1);
		Item moreItem1 = new Item();
		moreItem1.setId(1);
		moreItem1.setQuantity(1);
		s1.add(item1);
		s1.add(moreItem1);
		s1.add(item2);

		assertEquals("should have 2 of item 1", 2, s1.getQuantity(1));
		assertEquals("should have 1 of item 2", 1, s1.getQuantity(2));

	}

	@Test
	public void testSell() throws SQLException {
		SoldItem soldItem = new SoldItem();
		soldItem.setId(1);
		soldItem.setQuantity(2);
		assertFalse("Should not sell unexisting item", s1.sell(soldItem));
		Item item1 = new Item();
		item1.setId(1);
		item1.setQuantity(1);
		s1.add(item1);
		assertFalse("Should not have enough quantity", s1.sell(soldItem));
		assertEquals("should still have 1 of item1", 1, s1.getQuantity(1));
		s1.add(item1);
		assertTrue("should sell normal", s1.sell(soldItem));
		assertEquals("should left 0 of item1", 0, s1.getQuantity(1));

	}
}
