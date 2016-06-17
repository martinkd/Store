package com.martin.core;

import java.sql.SQLException;
import java.util.List;

import com.martin.dao.ItemDao;
import com.martin.dao.SoldItemDao;
import com.martin.item.Item;
import com.martin.item.SoldItem;

public abstract class Store {
	protected SoldItemDao sIdao;
	protected ItemDao iDao;
	protected Warehouse wh;

	public Store() {
		sIdao = new SoldItemDao();
		iDao = new ItemDao();
		wh = new Warehouse();
	}

	public abstract boolean sell(SoldItem soldItem) throws SQLException;

	public List<SoldItem> getSells() throws SQLException {
		return sIdao.getAllSells();
	}

	protected void addSoldItemOrUpdateQuantity(boolean canSell, SoldItem soldItem, Item item) throws SQLException {
		if (canSell) {
			soldItem.setName(item.getName());
			soldItem.setSinglePrice(item.getSinglePrice());

			if (sIdao.contains(soldItem)) {
				soldItem.setQuantity(newQuantity(soldItem));
				sIdao.updateQuantity(soldItem);
			} else {
				sIdao.add(soldItem);
			}
		}
	}

	protected int newQuantity(SoldItem item) throws SQLException {
		SoldItem currentItem = sIdao.getItemById(item.getId());
		return currentItem.getQuantity() + item.getQuantity();
	}
	
}
