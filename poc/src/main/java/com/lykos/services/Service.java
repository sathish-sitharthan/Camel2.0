package com.lykos.services;

import com.lykos.model.Product;

public interface Service {
	
	String getString(String id);
	
	String createProduct(Product product);
	
	Product getProduct(String id);

}
