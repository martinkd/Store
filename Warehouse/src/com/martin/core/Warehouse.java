package com.martin.core;

import java.sql.SQLException;
import java.util.List;

import com.martin.dao.SellsDao;
import com.martin.dao.WarehouseDao;
import com.martin.item.Item;
import com.martin.item.SoldItem;

public class Warehouse {
	private WarehouseDao wDao;

	public Warehouse() {
		wDao = new WarehouseDao();
	}

	public Item getById(int id) throws SQLException {
		return wDao.getById(id);
	}

	public List<Item> getAllItems() throws SQLException {
		return wDao.getAllItems();
	}

	public void add(Item item) throws SQLException {
		if (exists(item)) {
			Item existingItem = wDao.getByName(item.getName());
			existingItem.setQuantity(increaseQuantity(existingItem, item.getQuantity()));
			wDao.updateQuantity(existingItem);
		} else {
			wDao.add(item);
		}
	}

	private boolean exists(Item item) throws SQLException {
		return wDao.getByName(item.getName()) != null;

	}

	private int increaseQuantity(Item existingItem, int addedQuantity) {
		int oldQuantity = existingItem.getQuantity();
		return oldQuantity + addedQuantity;
	}

	public boolean sendToStore(int id, int quantity) throws SQLException {
		Item item = wDao.getById(id);
		boolean canSend = item != null && decreaseQuantity(item, quantity) >= 0;
		if (canSend) {
			item.setQuantity(decreaseQuantity(item, quantity));
			wDao.updateQuantity(item);
		}
		return canSend;
	}

	public boolean delete(int id) throws SQLException {
		boolean canDelete = wDao.getById(id) != null;
		if (canDelete) {
			wDao.deleteItem(id);
		}
		return canDelete;
	}

	public boolean sell(SoldItem soldItem) throws SQLException {
		Item item = wDao.getById(soldItem.getId());
		boolean canSell = item != null && decreaseQuantity(item, soldItem.getQuantity()) >= 0;
		if (canSell) {
			item.setQuantity(decreaseQuantity(item, soldItem.getQuantity()));
			SellsDao.getInstance().add(soldItem);
		}
		return canSell;
	}

	private int decreaseQuantity(Item item, int quantity) {
		int oldQuantity = item.getQuantity();
		return oldQuantity - quantity;
	}
}