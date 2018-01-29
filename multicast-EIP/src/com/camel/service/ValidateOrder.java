package com.camel.service;

import com.camel.domain.Order;

public class ValidateOrder {
	public void validateOrder(Order order)	
	 {
		 if(order!=null)
		 {
			if(order.getOrderID().equals("D101"))
			{
				throw new NullPointerException("Exception : Already Order booked!"); 
			}
			else
			{
			 System.out.println("Validate Order ::"+ order);
			}
		 }
		
	 }
}
