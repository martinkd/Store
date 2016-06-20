package com.martin.warehouse.core;

import java.sql.SQLException;

import com.martin.warehouse.dao.SellsDao;
import com.martin.warehouse.dao.StoreOneDao;
import com.martin.warehouse.item.Item;
import com.martin.warehouse.item.SoldItem;

public class Store1 implements Storable {

	private StoreOneDao s1Dao;

	public Store1() {
		s1Dao = new StoreOneDao();
	}

	public int getQuantity(int id) throws SQLException {
		return s1Dao.getQuantity(id);
	}
	
	public void add(Item item) throws SQLException {
		if (exists(item)) {
			int newQuantity = increaseQuantity(item);
			s1Dao.updateQuantity(item.getId(), newQuantity);
		} else {
			s1Dao.add(item);
		}
	}

	public boolean exists(Item item) throws SQLException {
		return s1Dao.exists(item.getId());
	}

	public int increaseQuantity(Item item) throws SQLException {
		int oldQuantity = s1Dao.getQuantity(item.getId());
		return oldQuantity + item.getQuantity();
	}

	public boolean sell(SoldItem soldItem) throws SQLException {
		boolean canSell = s1Dao.exists(soldItem.getId()) && decreaseQuantity(soldItem) >= 0;
		if (canSell) {
			int newQuantity = decreaseQuantity(soldItem);
			s1Dao.updateQuantity(soldItem.getId(), newQuantity);
			SellsDao.getInstance().add(soldItem);
		}
		return canSell;
	}

	public int decreaseQuantity(SoldItem soldItem) throws SQLException {
		int oldQuantity = s1Dao.getQuantity(soldItem.getId());
		return oldQuantity - soldItem.getQuantity();
	}

}
