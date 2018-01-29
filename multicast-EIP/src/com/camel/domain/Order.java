package com.camel.domain;

public class Order {

	private String orderID;
	private String orderName;
	private String cost;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", orderName=" + orderName + ", cost=" + cost + "]";
	}
	
}
