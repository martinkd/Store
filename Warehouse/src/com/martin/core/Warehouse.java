package com.martin.core;

import java.sql.SQLException;
import java.util.List;

import com.martin.dao.ItemDao;
import com.martin.item.Item;

public class Warehouse {
	private ItemDao iDao;

	public Warehouse() {
		iDao = new ItemDao();
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
			int quantity = increasedMainStoreQuantity(item, currentItem);
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

	private int increasedMainStoreQuantity(Item item, Item currentItem) {
		int quantity = currentItem.getMainStoreQuantity();
		quantity += item.getMainStoreQuantity();
		return quantity;
	}

	public boolean sendToStore1(int id, int quantity) throws SQLException {
		Item currentItem = getById(id);
		if (canSend(currentItem, quantity)) {
			currentItem.setMainStoreQuantity(decreasedMainStoreQuantity(currentItem, quantity));
			currentItem.setStore1Quantity(increasedStore1Quantity(currentItem, quantity));
			iDao.updateQuantity(currentItem);
			return true;
		}
		return false;
	}

	private boolean canSend(Item currentItem, int quantity) throws SQLException {
		return currentItem != null && isEnoughQuantityInMainStore(currentItem, quantity);
	}

	protected boolean isEnoughQuantityInMainStore(Item item, int quantity) {
		return item.getMainStoreQuantity() > 0 && item.getMainStoreQuantity() >= quantity;
	}
	
	protected boolean isEnoughQuantityInStore1(Item item, int quantity) {
		return item.getStore1Quantity() > 0 && item.getStore1Quantity() >= quantity;
	}
	
	protected boolean isEnoughQuantityInStore2(Item item, int quantity) {
		return item.getStore2Quantity() > 0 && item.getStore2Quantity() >= quantity;
	}

	protected int decreasedMainStoreQuantity(Item currentItem, int quantity) {
		return currentItem.getMainStoreQuantity() - quantity;
	}

	private int increasedStore1Quantity(Item currentItem, int quantity) {
		return currentItem.getStore1Quantity() + quantity;
	}

	public boolean sendToStore2(int id, int quantity) throws SQLException {
		Item currentItem = getById(id);
		if (canSend(currentItem, quantity)) {
			currentItem.setMainStoreQuantity(decreasedMainStoreQuantity(currentItem, quantity));
			currentItem.setStore2Quantity(increasedStore2Quantity(currentItem, quantity));
			iDao.updateQuantity(currentItem);
			return true;
		}
		return false;
	}

	private int increasedStore2Quantity(Item currentItem, int quantity) {
		return currentItem.getStore2Quantity() + quantity;
	}

	public boolean delete(int id) throws SQLException {
		boolean canDelete = false;
		if (iDao.getByIdItemOrNull(id) != null) {
			iDao.deleteItem(id);
			canDelete = true;
		}
		return canDelete;
	}

}
