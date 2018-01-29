package com.lykos.model;

public class MyLogBean {
	
	
    public void logit() {
    	System.out.println("Logging");       
    }
    
    public void seeProduct(Product obj){
    	System.out.println("Product::"+obj.getProductId());
    	System.out.println("Product::"+obj.getProductName());
    }
    
    public void getString(String obj){
    	System.out.println("Product::"+obj.toString());
    	
    }
    
      
     
      
}
