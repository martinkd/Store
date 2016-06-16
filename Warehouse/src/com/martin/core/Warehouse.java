package com.martin.core;

import java.sql.SQLException;
import java.util.List;

import com.martin.dao.ItemDao;
import com.martin.dao.SoldItemDao;
import com.martin.item.Item;

public class Warehouse {
	private ItemDao iDao;
	private SoldItemDao soldIdao;

	public Warehouse() {
		iDao = new ItemDao();
		//soldIdao = new SoldItemDao();
	}

	public Item getById(int id) throws SQLException {
		return iDao.getByIdItemOrNull(id);
	}

	public List<Item> getAllItems() throws SQLException {
		return iDao.getAllItems();
	}

	public void addInMainStore(Item item) throws SQLException {
		if (exists(item)) {
			Item currentItem = iDao.getByNameItemOrNull(item.getName());
			int quantity = calculateQuantity(item, currentItem);
			currentItem.setMainStoreQuantity(quantity);
			iDao.updateQuantity(currentItem);
		} else {
			iDao.add(item);
		}
	}

	private boolean exists(Item item) throws SQLException {
		boolean exists = true;
		if (iDao.getByNameItemOrNull(item.getName()) == null) {
			exists = false;
		}
		return exists;
	}

	private int calculateQuantity(Item item, Item currentItem) {
		int quantity = currentItem.getMainStoreQuantity();
		quantity += item.getMainStoreQuantity();
		return quantity;
	}

	public boolean sendToStore1(int id, int quantity) throws SQLException {
		Item currentItem = getById(id);
		if (canSend(currentItem, quantity)) {
			currentItem.setMainStoreQuantity(newMainStoreQuantity(currentItem, quantity));
			currentItem.setStore1Quantity(newStore1Quantity(currentItem, quantity));
			iDao.updateQuantity(currentItem);
			return true;
		}
		return false;
	}

	private boolean canSend(Item currentItem, int quantity) throws SQLException {
		return currentItem != null && isEnoughQuantity(currentItem, quantity);
	}

	private boolean isEnoughQuantity(Item item, int quantity) {
		return item.getMainStoreQuantity() > 0 && item.getMainStoreQuantity() >= quantity;
	}

	private int newMainStoreQuantity(Item currentItem, int quantity) {
		return currentItem.getMainStoreQuantity() - quantity;
	}

	private int newStore1Quantity(Item currentItem, int quantity) {
		return currentItem.getStore1Quantity() + quantity;
	}

	public boolean sendToStore2(int id, int quantity) throws SQLException {
		Item currentItem = getById(id);
		if (canSend(currentItem, quantity)) {
			currentItem.setMainStoreQuantity(newMainStoreQuantity(currentItem, quantity));
			currentItem.setStore2Quantity(newStore2Quantity(currentItem, quantity));
			iDao.updateQuantity(currentItem);
			return true;
		}
		return false;
	}

	private int newStore2Quantity(Item currentItem, int quantity) {
		return currentItem.getStore2Quantity() + quantity;
	}
	public static void main(String[] args) {
		Warehouse wh = new Warehouse();
	}

}
