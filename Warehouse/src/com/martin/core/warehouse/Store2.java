package com.martin.core.warehouse;

import java.sql.SQLException;

import com.martin.dao.warehouse.SellsDao;
import com.martin.dao.warehouse.StoreTwoDao;
import com.martin.item.warehouse.Item;
import com.martin.item.warehouse.SoldItem;

public class Store2 implements Storable {

	private StoreTwoDao s2Dao;

	public Store2() {
		s2Dao = new StoreTwoDao();
	}

	public void add(Item item) throws SQLException {
		if (exists(item)) {
			int newQuantity = increaseQuantity(item);
			s2Dao.updateQuantity(item.getId(), newQuantity);
		} else {
			s2Dao.add(item);
		}
	}

	public boolean exists(Item item) throws SQLException {
		return s2Dao.exists(item.getId());
	}

	public int increaseQuantity(Item item) throws SQLException {
		int oldQuantity = s2Dao.getQuantity(item.getId());
		return oldQuantity + item.getQuantity();
	}

	public boolean sell(SoldItem soldItem) throws SQLException {
		boolean canSell = s2Dao.exists(soldItem.getId()) && decreaseQuantity(soldItem) >= 0;
		if (canSell) {
			int newQuantity = decreaseQuantity(soldItem);
			s2Dao.updateQuantity(soldItem.getId(), newQuantity);
			SellsDao.getInstance().add(soldItem);
		}
		return canSell;
	}

	public int decreaseQuantity(SoldItem soldItem) throws SQLException {
		int oldQuantity = s2Dao.getQuantity(soldItem.getId());
		return oldQuantity - soldItem.getQuantity();
	}
}
