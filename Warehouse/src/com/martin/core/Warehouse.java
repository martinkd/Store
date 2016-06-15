package com.martin.core;

import java.sql.SQLException;

import com.martin.dao.ItemDao;
import com.martin.dao.SoldItemDao;
import com.martin.item.Item;

public class Warehouse {
	private ItemDao iDao;
	private SoldItemDao soldIdao;

	public Warehouse() {
		iDao = new ItemDao();
		soldIdao = new SoldItemDao();
	}

	public void addInMainStore(Item item) throws SQLException {
		if (exists(item)) {
			Item currentItem = iDao.getByNameItemOrNull(item.getName());
			int quantity = calculateQuantity(item, currentItem);
			item.setMainStoreQuantity(quantity);
			iDao.updateQuantity(currentItem, item);
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

}
