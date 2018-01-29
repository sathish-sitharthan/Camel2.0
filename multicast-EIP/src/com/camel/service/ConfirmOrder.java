package com.camel.service;

import com.camel.domain.Order;

public class ConfirmOrder {
	public void confirmOrder(Order order)	
	 {
		 if(order.getOrderID()!=null ||(!order.getOrderID().isEmpty()))
		 {
			 System.out.println("Order is confirmed :"+order);
		 }
	 }
}
