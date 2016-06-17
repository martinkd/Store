package com.martin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.martin.item.SoldItem;

public class SoldItemDao {
	private Statement st;
	private PreparedStatement prepSt;
	private ResultSet rs;
	private WarehouseConnection warehouse;

	public SoldItemDao() {
		warehouse = new WarehouseConnection();
	}

	public boolean contains(SoldItem item) throws SQLException {
		String sql = "SELECT id FROM sold_item WHERE id = ?";
		prepSt = warehouse.createPreparedStatement(sql);
		prepSt.setInt(1, item.getId());
		rs = prepSt.executeQuery();
		return rs.next();
	}

	public List<SoldItem> getAllSells() throws SQLException {
		String sql = "SELECT * FROM sold_item;";
		st = warehouse.createStatement();
		rs = st.executeQuery(sql);
		List<SoldItem> sells = new ArrayList<SoldItem>();
		while (rs.next()) {
			SoldItem item = convertRowToItem(rs);
			sells.add(item);
		}
		return sells;
	}

	public SoldItem getItemById(int id) throws SQLException {
		String sql = "SELECT * FROM sold_item WHERE id = ?";
		prepSt = warehouse.createPreparedStatement(sql);
		prepSt.setInt(1, id);
		rs = prepSt.executeQuery();
		SoldItem item = null;
		if (rs.next()) {
			item = convertRowToItem(rs);
		}
		return item;
	}

	private SoldItem convertRowToItem(ResultSet rs) throws SQLException {
		SoldItem currentItem = new SoldItem();
		currentItem.setId(rs.getInt("id"));
		currentItem.setName(rs.getString("name"));
		currentItem.setSinglePrice(rs.getDouble("single_price"));
		currentItem.setQuantity(rs.getInt("quantity"));
		currentItem.setClient(rs.getString("client"));
		return currentItem;
	}

	public void add(SoldItem item) throws SQLException {
		String sql = "INSERT INTO sold_item (id, name, single_price, quantity, client) " + "VALUES (?, ?, ?, ?, ?);";
		prepSt = warehouse.createPreparedStatement(sql);
		prepSt.setInt(1, item.getId());
		prepSt.setString(2, item.getName());
		prepSt.setDouble(3, item.getSinglePrice());
		prepSt.setInt(4, item.getQuantity());
		prepSt.setString(5, item.getClient());
		prepSt.executeUpdate();
	}

	public void updateQuantity(SoldItem item) throws SQLException {
		String sql = "UPDATE sold_item SET quantity = ? WHERE id = ?;";
		prepSt = warehouse.createPreparedStatement(sql);
		prepSt.setInt(1, item.getQuantity());
		prepSt.setInt(2, item.getId());
		prepSt.executeUpdate();
	}
}
