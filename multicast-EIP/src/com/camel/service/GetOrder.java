package com.camel.service;

import com.camel.domain.Order;

public class GetOrder {
	public void getOrder(Order order)	
	 {
		 if(order==null)
		 {
			  throw new NullPointerException("Exception : Order can not be empty !"); 
		 }
		 else
		 {
			 System.out.println("Order is recived :"+order);
		 }
	 }
}
