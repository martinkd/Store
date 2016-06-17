package com.martin.core;

import java.sql.SQLException;

import com.martin.item.Item;
import com.martin.item.SoldItem;

public class Store1 extends Store {

	@Override
	public boolean sell(SoldItem soldItem) throws SQLException {
		Item item = wh.getById(soldItem.getId());
		boolean canSell = item != null && decreaseQuantityInStore1(item, soldItem);
		addSoldItemOrUpdateQuantity(canSell, soldItem, item);
		return canSell;
	}

	private boolean decreaseQuantityInStore1(Item item, SoldItem soldItem) throws SQLException {
		if (item != null && wh.isEnoughQuantityInStore1(item, soldItem.getQuantity())) {
			int quantity = decreasedStore1Quantity(item, soldItem.getQuantity());
			item.setStore1Quantity(quantity);
			iDao.updateQuantity(item);
			return true;
		}
		return false;
	}

	private int decreasedStore1Quantity(Item item, int quantity) {
		return item.getStore1Quantity() - quantity;
	}

}
