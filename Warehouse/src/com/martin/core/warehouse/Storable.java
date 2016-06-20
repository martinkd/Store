package com.martin.core.warehouse;

import java.sql.SQLException;

import com.martin.item.warehouse.Item;
import com.martin.item.warehouse.SoldItem;

public interface Storable {

	public void add(Item item) throws SQLException;

	boolean exists(Item item) throws SQLException;

	int increaseQuantity(Item item) throws SQLException;

	boolean sell(SoldItem soldItem) throws SQLException;

	int decreaseQuantity(SoldItem soldItem) throws SQLException;

}
