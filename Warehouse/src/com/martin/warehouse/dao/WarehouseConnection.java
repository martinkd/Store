package com.martin.warehouse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WarehouseConnection {
	private static String dbUrl = "jdbc:mysql://localhost:3306/warehouse?useSSL=false";
	private static String user = "student";
	private static String pass = "student";
	private Connection connection;
	private static WarehouseConnection warehouse = null;

	private WarehouseConnection() {
	}

	public static WarehouseConnection getInstance() {
		if (warehouse == null) {
			warehouse = new WarehouseConnection();
		}
		warehouse.setConnection();
		warehouse.createTables();
		return warehouse;
	}

	public Connection getConnection() {
		return connection;
	}

	private void setConnection() {
		try {
			connection = DriverManager.getConnection(dbUrl, user, pass);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql);
	}

	private void createTables() {
		try {
			createWarehouseTable();
			createStoreOneTable();
			createStoreTwoTable();
			createSellsTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createWarehouseTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS warehouse (" + "id INT NOT NULL AUTO_INCREMENT," + "name VARCHAR(45),"
				+ "distributor VARCHAR(45)," + "single_price DOUBLE," + "quantity INT," + "PRIMARY KEY(id)" + ");";
		getPreparedStatement(sql).execute();
	}

	private void createStoreOneTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS store_one (" + "id INT," + "quantity INT," + "PRIMARY KEY(id)" + ");";
		getPreparedStatement(sql).execute();
	}

	private void createStoreTwoTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS store_two (" + "id INT," + "quantity INT," + "PRIMARY KEY(id));";
		getPreparedStatement(sql).execute();
	}

	private void createSellsTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS sells(" + "id INT NOT NULL," + "name VARCHAR(45),"
				+ "single_price DOUBLE," + "quantity INT," + "client VARCHAR(45)" + ");";
		getPreparedStatement(sql).execute();
	}

	public void dropTables() {
		try {
			dropWarehouseTable();
			dropStoreOneTable();
			dropStoreTwoTable();
			dropSellsTable();
		} catch (SQLException e) {
			System.out.println("Tables not deleted");
			e.printStackTrace();
		} 
	}

	private void dropWarehouseTable() throws SQLException {
		String sql = "DROP TABLE warehouse;";
		getPreparedStatement(sql).execute();
	}

	private void dropStoreOneTable() throws SQLException {
		String sql = "DROP TABLE store_one;";
		getPreparedStatement(sql).execute();
	}

	private void dropStoreTwoTable() throws SQLException {
		String sql = "DROP TABLE store_two;";
		getPreparedStatement(sql).execute();
	}

	private void dropSellsTable() throws SQLException {
		String sql = "DROP TABLE sells;";
		getPreparedStatement(sql).execute();
	}

	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
