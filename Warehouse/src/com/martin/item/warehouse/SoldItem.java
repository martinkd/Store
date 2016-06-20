package com.martin.item.warehouse;

public class SoldItem extends Item {
	private String client;

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
