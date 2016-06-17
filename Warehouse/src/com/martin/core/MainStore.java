package com.martin.core;

import java.sql.SQLException;

import com.martin.item.Item;
import com.martin.item.SoldItem;

public class MainStore extends Store {

	@Override
	public boolean sell(SoldItem soldItem) throws SQLException {
		Item item = wh.getById(soldItem.getId());
		boolean canSell = item != null && decreaseQuantityInMainStore(item, soldItem);
		addSoldItemOrUpdateQuantity(canSell, soldItem, item);
		return canSell;
	}

	private boolean decreaseQuantityInMainStore(Item item, SoldItem soldItem) throws SQLException {
		if (item != null && wh.isEnoughQuantityInMainStore(item, soldItem.getQuantity())) {
			int quantity = wh.decreasedMainStoreQuantity(item, soldItem.getQuantity());
			item.setMainStoreQuantity(quantity);
			iDao.updateQuantity(item);
			return true;
		}
		return false;
	}

}
