package com.martin.core;

import java.sql.SQLException;

import com.martin.item.Item;
import com.martin.item.SoldItem;

public interface Storable {

	public void add(Item item) throws SQLException;

	boolean exists(Item item) throws SQLException;

	int increaseQuantity(Item item) throws SQLException;

	boolean sell(SoldItem soldItem) throws SQLException;

	int decreaseQuantity(SoldItem soldItem) throws SQLException;

}
