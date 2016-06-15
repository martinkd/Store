package com.martin.item;

public class SoldItem extends Item {
	private int quantity;
	private String client;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Override
	public double getTotalPrice() {
		return getSinglePrice() * getQuantity();
	}

	@Override
	public String toString() {
		return String.format("%s. id: %s name: %s singleP: %s quantity: %s client: %s totalPrice: %s", incrementRow(),
				getId(), getName(), getSinglePrice(), getQuantity(), getClient(), getTotalPrice());
	}
}
