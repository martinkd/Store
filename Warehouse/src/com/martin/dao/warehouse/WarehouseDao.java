package com.martin.dao.warehouse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.martin.item.warehouse.Item;

public class WarehouseDao {
	private ResultSet rs;
	private PreparedStatement prepSt;

	public List<Item> getAllItems() throws SQLException {
		try {
			String sql = "SELECT * FROM warehouse;";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			rs = prepSt.executeQuery();
			List<Item> items = new ArrayList<Item>();
			while (rs.next()) {
				Item item = convertRowToItem(rs);
				items.add(item);
			}
			return items;
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	public Item getById(int id) throws SQLException {
		try {
			String sql = "SELECT * FROM warehouse WHERE id = ?";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			rs = prepSt.executeQuery();
			Item item = null;
			if (rs.next()) {
				item = convertRowToItem(rs);
			}
			return item;
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	public Item getByName(String name) throws SQLException {
		try {
			String sql = "SELECT * FROM warehouse WHERE name = ?";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setString(1, name);
			rs = prepSt.executeQuery();
			Item item = null;
			if (rs.next()) {
				item = convertRowToItem(rs);
			}
			return item;
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	private Item convertRowToItem(ResultSet rs) throws SQLException {
		Item currentItem = new Item();
		currentItem.setId(rs.getInt("id"));
		currentItem.setName(rs.getString("name"));
		currentItem.setDistributor(rs.getString("distributor"));
		currentItem.setSinglePrice(rs.getDouble("single_price"));
		currentItem.setQuantity(rs.getInt("quantity"));
		return currentItem;
	}

	public void add(Item item) throws SQLException {
		try {
			String sql = "INSERT INTO warehouse (name, distributor, single_price, quantity) " + "VALUES (?, ?, ?, ?);";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			convertItemToRow(item, prepSt);
			prepSt.executeUpdate();
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	private void convertItemToRow(Item item, PreparedStatement prepSt) throws SQLException {
		prepSt.setString(1, item.getName());
		prepSt.setString(2, item.getDistributor());
		prepSt.setDouble(3, item.getSinglePrice());
		prepSt.setInt(4, item.getQuantity());
	}

	public void updateQuantity(Item currentItem) throws SQLException {
		try {
			String sql = "UPDATE warehouse SET quantity = ? WHERE id = ?;";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, currentItem.getQuantity());
			prepSt.setInt(2, currentItem.getId());
			prepSt.executeUpdate();
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	public void deleteItem(int id) throws SQLException {
		try {
			String sql = "DELETE FROM warehouse WHERE id = ?;";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			prepSt.executeUpdate();
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

}
