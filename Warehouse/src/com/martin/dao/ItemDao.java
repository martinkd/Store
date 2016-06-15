package com.martin.dao;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.martin.item.Item;

public class ItemDao {
	private ResultSet rs;
	private Statement st;
	private PreparedStatement prepSt;
	private WarehouseConnection warehouse;

	public ItemDao() {
		warehouse = new WarehouseConnection();
	}

	public List<Item> getAllItems() throws SQLException {
		String sql = "SELECT * FROM item;";
		st = warehouse.createStatement();
		rs = st.executeQuery(sql);
		List<Item> items = new ArrayList<Item>();
		while (rs.next()) {
			Item item = convertRowToItem(rs);
			items.add(item);
		}
		return items;
	}

	public Item getItemOrNull(int id) throws SQLException {
		String sql = "SELECT * FROM item WHERE id = ?";
		prepSt = warehouse.createPreparedStatement(sql);
		prepSt.setInt(1, id);
		rs = prepSt.executeQuery();
		Item item = null;
		if (rs.next()) {
			item = convertRowToItem(rs);
		}
		return item;
	}

	private Item convertRowToItem(ResultSet rs) throws SQLException {
		Item currentItem = new Item();
		currentItem.setId(rs.getInt("id"));
		currentItem.setName(rs.getString("name"));
		currentItem.setDistributor(rs.getString("distributor"));
		currentItem.setSinglePrice(rs.getDouble("single_price"));
		currentItem.setMainStoreQuantity(rs.getInt("main_store_quantity"));
		currentItem.setStore1Quantity(rs.getInt("store1_quantity"));
		currentItem.setStore2Quantity(rs.getInt("store2_quantity"));
		return currentItem;
	}

	public void add(Item item) throws SQLException {
		String sql = "INSERT INTO item (name, distributor, single_price, main_store_quantity) "
				+ "VALUES (?, ?, ?, ?);";
		prepSt = warehouse.createPreparedStatement(sql);
		convertItemToRow(item, prepSt);
		prepSt.executeUpdate();
	}

	private void convertItemToRow(Item item, PreparedStatement prepSt) throws SQLException {
		prepSt.setString(1, item.getName());
		prepSt.setString(2, item.getDistributor());
		prepSt.setDouble(3, item.getSinglePrice());
		prepSt.setInt(4, item.getMainStoreQuantity());
	}

	public void updateQuantity(Item currentItem, Item newItem) throws SQLException {
		String sql = "UPDATE item SET main_store_quantity = ?, store1_quantity = ?, store2_quantity = ? WHERE id = ?;";
		prepSt = warehouse.createPreparedStatement(sql);
		prepSt.setInt(1, newItem.getMainStoreQuantity());
		prepSt.setInt(2, newItem.getStore1Quantity());
		prepSt.setInt(3, newItem.getStore2Quantity());
		prepSt.setInt(4, currentItem.getId());
		prepSt.executeUpdate();
	}

	public void deleteItem(int id) throws SQLException {
		String sql = "DELETE FROM item WHERE id = ?;";
		prepSt = warehouse.createPreparedStatement(sql);
		prepSt.setInt(1, id);
		prepSt.executeUpdate();
	}
	
	public static void main(String[] args) throws SQLException {
		ItemDao dao = new ItemDao();
		dao.add(new Item());
		dao.add(new Item());
		dao.add(new Item());
		for (Item item : dao.getAllItems()) {
			System.out.println(item);
		}
	}

}
