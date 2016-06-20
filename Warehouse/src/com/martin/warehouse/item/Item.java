package com.martin.warehouse.item;

public class Item {
	private static int row = 1;
	private int id;
	private String name;
	private String distributor;
	private double singlePrice;
	private int quantity;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return getSinglePrice() * getQuantity();
	}

	@Override
	public String toString() {
		return String.format("%s. id: %s name: %s distributor: %s singleP: %s quantity: %s totalQ: %s totalP: %s",
				+incrementRow(), getId(), getName(), getDistributor(), getSinglePrice(), getQuantity(),
				getTotalPrice());
	}

}
