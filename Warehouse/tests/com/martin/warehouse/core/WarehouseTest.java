package com.martin.warehouse.core;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.martin.warehouse.core.Warehouse;
import com.martin.warehouse.dao.WarehouseConnection;
import com.martin.warehouse.item.Item;
import com.martin.warehouse.item.SoldItem;

public class WarehouseTest {

	private Warehouse wh;

	@Before
	public void setUp() {
		WarehouseConnection.getInstance().dropTables();
		wh = new Warehouse();
	}

	@Test
	public void testGetAllItems() throws SQLException {
		assertTrue("should be empty", wh.getAllItems().isEmpty());
		wh.add(new Item());
		wh.add(new Item());
		assertEquals("should have 2 items", 2, wh.getAllItems().size());
	}

	@Test
	public void testAdd() throws SQLException {
		Item item1 = new Item();
		item1.setName("LENOVO G50-30");
		Item item2 = new Item();
		item2.setName("ACER ASPIRE R7");
		Item moreItem1 = new Item();
		moreItem1.setName("LENOVO G50-30");
		moreItem1.setQuantity(1);
		wh.add(item1);
		wh.add(item2);
		wh.add(moreItem1);
		assertEquals("should have 2 items", 2, wh.getAllItems().size());
		assertEquals("quantity should be 1", 1, wh.getById(1).getQuantity());
		assertEquals("quantity should be 0", 0, wh.getById(2).getQuantity());
	}

	@Test
	public void testGetById() throws SQLException {
		assertNull("should not exists", wh.getById(1));
		Item item = new Item();
		wh.add(item);
		assertNotNull("should exists", wh.getById(1));
	}

	@Test
	public void sendToStore() throws SQLException {
		assertFalse("should not exist id 1", wh.sendToStore(1, 1));
		Item item = new Item();
		item.setName("LENOVO G50-30");
		wh.add(item);
		assertFalse("should not have any quantity", wh.sendToStore(1, 1));
		item.setQuantity(1);
		wh.add(item);
		assertFalse("should not have enough quantity", wh.sendToStore(1, 2));
		assertEquals("quantity should be still 1", 1, wh.getById(1).getQuantity());
		assertTrue("should send 1 item", wh.sendToStore(1, 1));
		assertEquals("should left with 0 items with id 1", 0, wh.getById(1).getQuantity());
	}

	@Test
	public void testDelete() throws SQLException {
		assertFalse("Should not delete unexisting item", wh.delete(1));
		wh.add(new Item());
		assertTrue("should delete normal", wh.delete(1));
		assertNull("should be deleted", wh.getById(1));
	}

	@Test
	public void testSell() throws SQLException {
		SoldItem soldItem = new SoldItem();
		assertFalse("Should not sell unexisting item", wh.sell(soldItem));
		Item item = new Item();
		item.setQuantity(1);
		wh.add(item);
		soldItem.setId(1);
		soldItem.setQuantity(2);
		assertFalse("should not have enough quantity", wh.sell(soldItem));
		soldItem.setQuantity(1);
		assertTrue("should sell normal", wh.sell(soldItem));
		assertEquals("should be left with 0 quantity", 0, wh.getById(1).getQuantity());
	}

}
