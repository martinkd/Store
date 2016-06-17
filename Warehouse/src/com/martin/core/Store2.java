package com.martin.core;

import java.sql.SQLException;

import com.martin.item.Item;
import com.martin.item.SoldItem;

public class Store2 extends Store {

	@Override
	public boolean sell(SoldItem soldItem) throws SQLException {
		Item item = wh.getById(soldItem.getId());
		boolean canSell = item != null && decreaseQuantityInStore2(item, soldItem);
		addSoldItemOrUpdateQuantity(canSell, soldItem, item);
		return canSell;
	}

	private boolean decreaseQuantityInStore2(Item item, SoldItem soldItem) throws SQLException {
		if (item != null && wh.isEnoughQuantityInStore2(item, soldItem.getQuantity())) {
			int quantity = decreasedStore2Quantity(item, soldItem.getQuantity());
			item.setStore2Quantity(quantity);
			iDao.updateQuantity(item);
			return true;
		}
		return false;
	}

	private int decreasedStore2Quantity(Item item, int quantity) {
		return item.getStore2Quantity() - quantity;
	}

}
