package com.martin.item;

public class Item {
	private static int row = 1;
	private int id;
	private String name;
	private String distributor;
	private double singlePrice;
	private int mainStoreQunatity;
	private int store1quantity;
	private int store2quantity;

	protected int incrementRow() {
		return row++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public double getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(double singlePrice) {
		this.singlePrice = singlePrice;
	}

	public int getMainStoreQuantity() {
		return mainStoreQunatity;
	}

	public void setMainStoreQuantity(int quantity) {
		this.mainStoreQunatity = quantity;
	}

	public int getStore1Quantity() {
		return store1quantity;
	}

	public void setStore1Quantity(int quantity) {
		this.store1quantity = quantity;
	}

	public int getStore2Quantity() {
		return store2quantity;
	}

	public void setStore2Quantity(int quantity) {
		this.store2quantity = quantity;
	}

	public int getTotalQuantity() {
		return mainStoreQunatity + store1quantity + store2quantity;
	}

	public double getTotalPrice() {
		return getSinglePrice() * getTotalQuantity();
	}

	@Override
	public String toString() {
		return String.format(
				"%s. id: %s name: %s distributor: %s singleP: %s mainStQ: %s st1Q: %s st2Q: %s totalQ: %s totalP: %s",
				+incrementRow(), getId(), getName(), getDistributor(), getSinglePrice(), getMainStoreQuantity(),
				getStore1Quantity(), getStore2Quantity(), getTotalQuantity(), getTotalPrice());
	}

}
