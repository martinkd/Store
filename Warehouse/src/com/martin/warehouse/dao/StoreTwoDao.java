package com.martin.warehouse.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.martin.warehouse.item.Item;

public class StoreTwoDao {
	private ResultSet rs;
	private PreparedStatement prepSt;

	public boolean exists(int id) throws SQLException {
		String sql = "SELECT id FROM store_two WHERE id = ?;";
		prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
		prepSt.setInt(1, id);
		rs = prepSt.executeQuery();
		return rs.next();
	}

	public int getQuantity(int id) throws SQLException {
		try {
			String sql = "SELECT quantity FROM store_one WHERE id = ?;";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			rs = prepSt.executeQuery();
			int quantity = 0;
			if (rs.next()) {
				quantity = rs.getInt("quantity");
			}
			return quantity;
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	public void add(Item item) throws SQLException {
		try {
			String sql = "INSERT INTO store_two (id, quantity) VALUES (?,?);";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, item.getId());
			prepSt.setInt(2, item.getQuantity());
			prepSt.executeUpdate();
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	public void updateQuantity(int id, int quantity) throws SQLException {
		try {
			String sql = "UPDATE store_two SET quantity = ? WHERE id = ?;";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			prepSt.setInt(2, quantity);
			prepSt.executeUpdate();
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

}
