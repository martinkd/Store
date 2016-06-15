package com.martin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WarehouseConnection {
	private String dbUrl = "jdbc:mysql://localhost:3306/warehouse?useSSL=false";
	private String user = "student";
	private String pass = "student";

	private Connection connection;
	private Statement statement;

	public WarehouseConnection() {
		createTables();
	}

	public WarehouseConnection(String user, String pass) {
		this.user = user;
		this.pass = pass;
		createTables();
	}

	public Connection getConnection() throws SQLException {
		return connection = DriverManager.getConnection(dbUrl, user, pass);
	}

	public Statement createStatement() throws SQLException {
		getConnection();
		return statement = connection.createStatement();
	}

	public PreparedStatement createPreparedStatement(String sql) throws SQLException {
		getConnection();
		return (PreparedStatement) connection.prepareStatement(sql);
	}

	private void createTables() {
		try {
			createStatement();
			createItemTable();
			createSoldItemTable();
			System.out.println("tables created!");

		} catch (SQLException e) {
			System.err.println("Tables not created!");
			e.printStackTrace();
		}

	}

	private void createItemTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS item (" + "id INT NOT NULL AUTO_INCREMENT," + "name VARCHAR(45),"
				+ "distributor VARCHAR(45)," + "single_price DOUBLE," + "main_store_quantity INT,"
				+ "store1_quantity INT," + "store2_quantity INT," + "PRIMARY KEY(id)" + ");";
		statement.execute(sql);
	}

	private void createSoldItemTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS sold_item(" + "id INT NOT NULL," + "name VARCHAR(45),"
				+ "single_price DOUBLE," + "quantity INT," + "client VARCHAR(45)," + "PRIMARY KEY(id)" + ");";
		statement.execute(sql);
	}

	@SuppressWarnings("unused")
	private void dropTables() {
		try {
			createStatement();
			dropItem();
			dropSoldItem();
			System.out.println("tables deleted");
		} catch (SQLException e) {
			System.out.println("Tables not deleted");
			e.printStackTrace();
		}
	}

	private void dropItem() throws SQLException {
		String sql = "DROP TABLE item;";
		statement.execute(sql);
	}

	private void dropSoldItem() throws SQLException {
		String sql = "DROP TABLE sold_item;";
		statement.execute(sql);
	}

	public static void main(String[] args) {
		//WarehouseConnection wh = new WarehouseConnection();
		//wh.dropTables();
	}
}
