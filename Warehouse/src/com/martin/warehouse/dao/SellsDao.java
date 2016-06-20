package com.martin.warehouse.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.martin.warehouse.item.SoldItem;

public class SellsDao {
	private PreparedStatement prepSt;
	private ResultSet rs;
	private static SellsDao sellsDao = null;
	
	private SellsDao() {
		
	}
	
	public static SellsDao getInstance() {
		if (sellsDao == null) {
			sellsDao = new SellsDao();
		}
		return sellsDao;
	}
	
	public boolean contains(int id) throws SQLException {
		try {
			String sql = "SELECT id FROM sells WHERE id = ?;";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			rs = prepSt.executeQuery();
			return rs.next();
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	public List<SoldItem> getAllSells() throws SQLException {
		try {
			String sql = "SELECT * FROM sells;";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			rs = prepSt.executeQuery();
			List<SoldItem> sells = new ArrayList<SoldItem>();
			while (rs.next()) {
				SoldItem item = convertRowToItem(rs);
				sells.add(item);
			}
			return sells;
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

	public SoldItem getItemById(int id) throws SQLException {
		try {
			String sql = "SELECT * FROM sells WHERE id = ?";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, id);
			rs = prepSt.executeQuery();
			SoldItem item = null;
			if (rs.next()) {
				item = convertRowToItem(rs);
			}
			return item;
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
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
		try {
			String sql = "INSERT INTO sells (id, name, single_price, quantity, client) " + "VALUES (?, ?, ?, ?, ?);";
			prepSt = WarehouseConnection.getInstance().getConnection().prepareStatement(sql);
			prepSt.setInt(1, item.getId());
			prepSt.setString(2, item.getName());
			prepSt.setDouble(3, item.getSinglePrice());
			prepSt.setInt(4, item.getQuantity());
			prepSt.setString(5, item.getClient());
			prepSt.executeUpdate();
		} finally {
			WarehouseConnection.getInstance().closeConnection();
		}
	}

}
